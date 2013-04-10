package com.mavarazy.performance.infrastructure.repository.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.utils.CollectionUtils;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.FlowEventGenerator;
import com.mavarazy.performance.infrastructure.StoreSpecification;
import com.mavarazy.performance.infrastructure.repository.FlowEventRepository;
import com.mavarazy.performance.infrastructure.repository.FlowEventRepositoryFactory;
import com.mavarazy.performance.infrastructure.tailer.FlowEventTailerFactory;
import com.mavarazy.performance.infrastructure.tailer.file.FlowEventTailer;

abstract public class AbstractParallelFlowEventRepositoryToTailerTest {

    final private int numStorages;
    final private ExecutorService executorService;

    final private TestFlowEventListener flowEventListener = new TestFlowEventListener();

    final private List<StoreSpecification> storeStrategies = new ArrayList<StoreSpecification>();
    final private List<FlowEventRepository> flowEventRepositories = new ArrayList<FlowEventRepository>();
    final private List<FlowEventTailer> flowEventTailers = new ArrayList<FlowEventTailer>();

    protected AbstractParallelFlowEventRepositoryToTailerTest(final Collection<StoreSpecification> storeSpecifications) {
        this.numStorages = storeSpecifications.size();
        this.executorService = Executors.newFixedThreadPool(numStorages);
        this.storeStrategies.addAll(storeSpecifications);

        for (StoreSpecification storeSpecification : storeSpecifications) {
            FlowEventRepository flowEventRepository = FlowEventRepositoryFactory.createRepository(storeSpecification);
            FlowEventTailer flowEventTailer = FlowEventTailerFactory.create(storeSpecification);

            flowEventTailer.addListener(flowEventListener);

            flowEventRepositories.add(flowEventRepository);
            flowEventTailers.add(flowEventTailer);
        }
    }

    @After
    public void clean() {
        for (FlowEventTailer fileTailer : flowEventTailers)
            fileTailer.stop();
        for (StoreSpecification fileStoreStrategy : storeStrategies)
            fileStoreStrategy.clean();
    }

    private class Publisher implements Runnable {
        final private FlowEventRepository flowEventRepository;
        final private Collection<FlowEvent> flowEvents;

        public Publisher(FlowEventRepository flowEventRepository, Collection<FlowEvent> events) {
            this.flowEventRepository = flowEventRepository;
            this.flowEvents = events;
        }

        public void run() {
            try {
                for (FlowEvent flowEvent : flowEvents)
                    flowEventRepository.save(flowEvent);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @Test
    public void testSavingInOrder() {
        List<FlowEvent> events = FlowEventGenerator.randomFlow(numStorages * 100);
        List<Collection<FlowEvent>> eventCollections = CollectionUtils.split(events, numStorages);
        for (int i = 0; i < numStorages; i++) {
            executorService.execute(new Publisher(flowEventRepositories.get(i), eventCollections.get(i)));
        }

        Collection<FlowEvent> savedEvents = new ArrayList<FlowEvent>();
        for (int i = 0; i < events.size(); i++) {
            FlowEvent readEvent = flowEventListener.poll();
            Assert.assertNotNull(readEvent);
            savedEvents.add(readEvent);
        }

        Set<FlowEvent> sortedSavedEvents = new TreeSet<FlowEvent>(new Comparator<FlowEvent>() {
            @Override
            public int compare(FlowEvent o1, FlowEvent o2) {
                return o1.getFlowPosition().getPosition() - o2.getFlowPosition().getPosition();
            }
        });
        sortedSavedEvents.addAll(savedEvents);

        Assert.assertTrue(sortedSavedEvents.containsAll(events));
        Assert.assertTrue(events.containsAll(sortedSavedEvents));
    }

}
