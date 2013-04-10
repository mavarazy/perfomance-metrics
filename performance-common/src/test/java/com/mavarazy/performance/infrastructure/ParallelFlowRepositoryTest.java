package com.mavarazy.performance.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.FrequentRunner;
import org.junit.runners.RunTimes;

import com.mavarazy.performance.flow.event.FlowEvent;

@RunTimes(100)
@RunWith(FrequentRunner.class)
public class ParallelFlowRepositoryTest {

    final private int NUM_THREADS = 4;
    final private ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

    final static private Random random = new Random();

    final private FlowRepositoryImpl flowRepositoryImpl = new FlowRepositoryImpl();
    final private TestFlowRepositoryListener listener = new TestFlowRepositoryListener();
    {
        flowRepositoryImpl.addListener(listener);
    }

    final private CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(NUM_THREADS);

    @After
    public void clean() {
        executor.shutdown();
    }

    private class Publisher implements Runnable {
        final private Collection<FlowEvent> events;

        public Publisher(Collection<FlowEvent> flowEvents) {
            events = flowEvents;
        }

        @Override
        public void run() {
            try {
                for (FlowEvent flowEvent : events) {
                    flowRepositoryImpl.flowEventAdded(flowEvent);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                COUNT_DOWN_LATCH.countDown();
            }
        }

    }

    @Test
    public void testParallelSequentialFlowEventPublishing() {
        testProcessing(FlowEventGenerator.randomFlow(NUM_THREADS * 100));
    }
    
    @Test
    public void testParallelShuffledFlowEventPublishing() {
        List<FlowEvent> flowEvents = FlowEventGenerator.randomFlow(NUM_THREADS * 100);
        Collections.shuffle(flowEvents);
        
        testProcessing(flowEvents);
    }

    public void testProcessing(Collection<FlowEvent> flowEvents) {
        Map<Integer, Collection<FlowEvent>> choppedEvents = new HashMap<Integer, Collection<FlowEvent>>();
        for (FlowEvent event : flowEvents) {
            int selection = random.nextInt(NUM_THREADS);
            if (choppedEvents.get(selection) == null)
                choppedEvents.put(selection, new ArrayList<FlowEvent>());
            choppedEvents.get(selection).add(event);
        }

        for (Collection<FlowEvent> events : choppedEvents.values()) {
            executor.submit(new Publisher(events));
        }

        try {
            COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(listener.poll());
    }

}
