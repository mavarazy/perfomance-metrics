package com.mavarazy.performance.flow.group;

import java.util.ArrayList;
import java.util.Collection;

import com.mavarazy.performance.flow.Flow;

public class FlowGroup<T> {

    final private T identifier;
    final private Collection<Flow> flows = new ArrayList<Flow>();

    public FlowGroup(T identifier) {
        assert identifier != null : "Invocation can't be null";
        this.identifier = identifier;
    }

    public T getGroupIdentifier() {
        return identifier;
    }

    public Collection<Flow> getFlows() {
        return flows;
    }

    public void addFlow(Flow flow) {
        flows.add(flow);
    }
}
