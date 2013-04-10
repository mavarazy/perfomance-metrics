package com.mavarazy.performance.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.FrequentRunner;
import org.junit.runners.RunTimes;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.FlowPosition;
import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.builder.FlowConstructor;

@RunTimes(1)
@RunWith(FrequentRunner.class)
public class FlowConstructorTest {

    final static private String DEFAULT_FLOW = "testFlow";
    final static private int DEFAULT_DEPTH = 500;

    @Test
    public void testSequentialFlowEventProcessing() {
        List<FlowEvent> flowEvents = FlowEventGenerator.randomFlow(DEFAULT_DEPTH);

        checkReconstructable(flowEvents);
    }

    @Test
    public void testShuffledFlowEventProcessing() {
        List<FlowEvent> flowEvents = FlowEventGenerator.randomFlow(DEFAULT_DEPTH);
        Collections.shuffle(flowEvents);

        checkReconstructable(flowEvents);
    }

    @Test
    public void testSpecialCases() {
        List<FlowEvent> flowEvents = specialCases();
        checkReconstructable(flowEvents);
    }

    private void checkReconstructable(Collection<FlowEvent> flowEvents) {
        FlowConstructor flowConstructor = FlowConstructor.create(DEFAULT_FLOW);
        for (FlowEvent event : flowEvents) {
            flowConstructor.add(event);
        }
        Assert.assertTrue(flowConstructor.complete());
    }

    private List<FlowEvent> specialCases() {
        List<FlowEvent> specialCase = new ArrayList<FlowEvent>();
        Invocation primaryInvocation = Invocation.valueOf("Class_0", "method_0");
        Invocation secondInvocation = Invocation.valueOf("Class_1", "method_1");
        Invocation therdInvocation = Invocation.valueOf("Class_2", "method_2");
        Invocation forthInvocation = Invocation.valueOf("Class_3", "method_3");
        Invocation fithInvocation = Invocation.valueOf("Class_3", "method_5");

        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallEndEvent(primaryInvocation), new FlowPosition(DEFAULT_FLOW, 9)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallStartedEvent(therdInvocation), new FlowPosition(DEFAULT_FLOW, 2)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallStartedEvent(secondInvocation), new FlowPosition(DEFAULT_FLOW, 1)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallStartedEvent(fithInvocation), new FlowPosition(DEFAULT_FLOW, 5)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallEndEvent(forthInvocation), new FlowPosition(DEFAULT_FLOW, 4)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallStartedEvent(forthInvocation), new FlowPosition(DEFAULT_FLOW, 3)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallEndEvent(secondInvocation), new FlowPosition(DEFAULT_FLOW, 8)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallStartedEvent(primaryInvocation), new FlowPosition(DEFAULT_FLOW, 0)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallEndEvent(fithInvocation), new FlowPosition(DEFAULT_FLOW, 6)));
        specialCase.add(FlowEventGenerator.generate(CallEventGenerator.generateCallEndEvent(therdInvocation), new FlowPosition(DEFAULT_FLOW, 7)));

        return specialCase;
    }
}