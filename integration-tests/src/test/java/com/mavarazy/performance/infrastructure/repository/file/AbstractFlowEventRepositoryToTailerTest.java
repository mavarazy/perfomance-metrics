package com.mavarazy.performance.infrastructure.repository.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.FrequentRunner;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.FlowEventGenerator;
import com.mavarazy.performance.infrastructure.StoreSpecification;
import com.mavarazy.performance.infrastructure.repository.FlowEventRepository;
import com.mavarazy.performance.infrastructure.repository.FlowEventRepositoryFactory;
import com.mavarazy.performance.infrastructure.tailer.FlowEventTailerFactory;
import com.mavarazy.performance.infrastructure.tailer.file.FlowEventTailer;

@RunWith(FrequentRunner.class)
abstract public class AbstractFlowEventRepositoryToTailerTest {

    final private FlowEventRepository fileFlowEventRepository;
    final private FlowEventTailer fileTailer;
    final private StoreSpecification storeSpecification;
    final private TestFlowEventListener flowEventListener;

    protected AbstractFlowEventRepositoryToTailerTest(StoreSpecification specification) {
        this.storeSpecification = specification;
        this.fileFlowEventRepository = FlowEventRepositoryFactory.createRepository(specification);
        this.fileTailer = FlowEventTailerFactory.create(specification);
        this.flowEventListener = new TestFlowEventListener();

        fileTailer.addListener(flowEventListener);
    }

    @After
    public void clean() {
        fileTailer.stop();
        storeSpecification.clean();
    }

    @Test
    public void testSavingInOrder() {
        Collection<FlowEvent> events = FlowEventGenerator.randomFlow();
        for (FlowEvent event : events)
            fileFlowEventRepository.save(event);

        Collection<FlowEvent> savedEvents = new ArrayList<FlowEvent>();
        for (int i = 0; i < events.size(); i++) {
            FlowEvent readEvent = flowEventListener.poll();
            Assert.assertNotNull(readEvent);
            savedEvents.add(readEvent);
        }

        Assert.assertTrue(savedEvents.containsAll(events));
        Assert.assertTrue(events.containsAll(savedEvents));
    }

    @Test
    public void testSavingShuffled() {
        List<FlowEvent> events = FlowEventGenerator.randomFlow();
        Collections.shuffle(events);
        for (FlowEvent event : events)
            fileFlowEventRepository.save(event);

        Collection<FlowEvent> savedEvents = new ArrayList<FlowEvent>();
        for (int i = 0; i < events.size(); i++) {
            FlowEvent readEvent = flowEventListener.poll();
            Assert.assertNotNull(readEvent);
            savedEvents.add(readEvent);
        }

        Assert.assertTrue(savedEvents.containsAll(events));
        Assert.assertTrue(events.containsAll(savedEvents));
    }
}
