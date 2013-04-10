package com.mavarazy.performance.flow;

import java.util.Collection;
import java.util.Collections;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.event.FlowEvent;

public class Flow {

    final private Invocation invocation;
    final private FlowEvent startEvent;
    final private FlowEvent endEvent;
    final private Collection<Flow> subFlows;

    @SuppressWarnings("unchecked")
    public Flow(FlowEvent start, FlowEvent end, Collection<Flow> subFlows) {
        assert start.getCallEvent().getInvocation() == end.getCallEvent().getInvocation() : "Flow must have the same invocations as sources";
        this.startEvent = start;
        this.endEvent = end;
        this.invocation = start.getCallEvent().getInvocation();
        this.subFlows = subFlows != null && subFlows.size() > 0 ? subFlows : Collections.EMPTY_LIST;
    }

    public FlowEvent getStart() {
        return startEvent;
    }

    public FlowEvent getEnd() {
        return endEvent;
    }

    public Collection<Flow> getSubFlows() {
        return subFlows;
    }

    public Invocation getInvocation() {
        return invocation;
    }

}
