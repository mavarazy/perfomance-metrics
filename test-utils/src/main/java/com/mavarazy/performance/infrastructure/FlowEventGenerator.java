package com.mavarazy.performance.infrastructure;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.FlowPosition;
import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.event.FlowEvent;

public class FlowEventGenerator {

    final static private FlowPosition DEFAULT_POSITION = new FlowPosition("test-position", 1);
    final static private Collection<CallMetric> DEFAULT_METRICS = new ArrayList<CallMetric>();

    final static private Random random = new Random();

    static public FlowEvent random() {
        return generate(CallEventGenerator.random(), CallMetricGenerator.random());
    }

    static public List<FlowEvent> randomFlow() {
        return randomFlow((8 + random.nextInt(50)));
    }

    static public List<FlowEvent> randomFlow(int calls) {
        List<FlowEvent> flowEvents = randomFlow(new ArrayList<FlowEvent>(), 0, calls * 2, 0);
        return flowEvents;
    }

    static private List<FlowEvent> randomFlow(List<FlowEvent> eventAccumulator, int startPosition, int endPosition, int currentDepth) {
        Invocation invocation = Invocation.valueOf("Class_" + currentDepth, "method_" + startPosition);
        eventAccumulator.add(generate(CallEventGenerator.generateCallStartedEvent(invocation), new FlowPosition("testFlow", startPosition)));
        if (startPosition + 1 != endPosition) {
            int newStartPosition = startPosition + 1;
            int newEndPosition = endPosition - 1;
            int distance = newEndPosition - newStartPosition;
            if (distance == 1) {
                randomFlow(eventAccumulator, newStartPosition, newEndPosition, currentDepth + 1);
            } else {
                int numPossibleCalls = (distance + 1) >> 1;
                int subCalls = 1 + random.nextInt(numPossibleCalls);
                int numFreeCalls = numPossibleCalls - subCalls;
                for (int i = 0; i < subCalls; i++) {
                    int additionalCalls = numFreeCalls > 0 && i != (subCalls - 1) ? random.nextInt(numFreeCalls) : numFreeCalls;
                    numFreeCalls -= additionalCalls;
                    newEndPosition = newStartPosition + 1 + (additionalCalls) * 2;
                    randomFlow(eventAccumulator, newStartPosition, newEndPosition, currentDepth + 1);
                    newStartPosition = newEndPosition + 1;
                }
            }
        }
        eventAccumulator.add(generate(CallEventGenerator.generateCallEndEvent(invocation), new FlowPosition("testFlow", startPosition == 0 ? endPosition - 1
                : endPosition)));
        return eventAccumulator;
    }

    static public FlowEvent generate(CallEvent callEvent, FlowPosition position) {
        return new FlowEvent(position, callEvent, DEFAULT_METRICS);
    }

    static public FlowEvent generate(CallEvent callEvent) {
        return generate(callEvent, DEFAULT_POSITION);
    }

    static public FlowEvent generate(CallEvent callEvent, CallMetric callMetric) {
        Collection<CallMetric> callMetrics = new ArrayList<CallMetric>();
        callMetrics.add(callMetric);
        return generate(callEvent, callMetrics);
    }

    static public FlowEvent generate(CallEvent callEvent, Collection<CallMetric> callMetric) {
        return new FlowEvent(DEFAULT_POSITION, callEvent, callMetric);
    }
}
