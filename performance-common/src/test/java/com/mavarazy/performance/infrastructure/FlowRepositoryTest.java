package com.mavarazy.performance.infrastructure;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.FrequentRunner;
import org.junit.runners.RunTimes;

import com.mavarazy.performance.flow.event.FlowEvent;

@RunTimes(1)
@RunWith(FrequentRunner.class)
public class FlowRepositoryTest {
    final private FlowRepositoryImpl flowRepositoryImpl = new FlowRepositoryImpl();

    final private ExecutorService executor = Executors.newFixedThreadPool(4);

    final private TestFlowRepositoryListener listener = new TestFlowRepositoryListener();
    {
        flowRepositoryImpl.addListener(listener);
    }

    @After
    public void clean() {
        executor.shutdown();
    }

    @Test
    public void testSequentialFlowEventPublishing() {
        List<FlowEvent> flowEvents = FlowEventGenerator.randomFlow();
        publish(flowEvents);
        Assert.assertNotNull(listener.poll());
    }

    @Test
    public void testShuffledFlowEventPublishing() {
        List<FlowEvent> flowEvents = FlowEventGenerator.randomFlow();
        Collections.shuffle(flowEvents);
        publish(flowEvents);
        Assert.assertNotNull(listener.poll());
    }

    private void publish(Collection<FlowEvent> flowEvents) {
        for (FlowEvent event : flowEvents) {
            flowRepositoryImpl.flowEventAdded(event);
        }
    }

}
