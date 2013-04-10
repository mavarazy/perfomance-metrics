package com.mavarazy.performance.infrastructure.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.mavarazy.performance.flow.Flow;
import com.mavarazy.performance.flow.event.FlowEvent;

public class FlowBuilder implements Comparable<FlowBuilder>{

    private FlowBuilder parent;
    final private Set<FlowBuilder> subEntries = new HashSet<FlowBuilder>();

    final private FlowEvent start;
    private FlowEvent end;

    public FlowBuilder(final FlowEvent start) {
        this.start = start;
    }

    public FlowEvent setEnd(FlowEvent value) {
        return end = value;
    }

    public FlowBuilder getParent() {
        return parent;
    }

    public void setParent(FlowBuilder parent) {
        this.parent = parent;
        this.parent.subEntries.add(this);
    }

    public int getStartPosition() {
        return start.getFlowPosition().getPosition();
    }

    public int getEndPosition() {
        return filled() ? end.getFlowPosition().getPosition() : 0;
    }

    public boolean filled() {
        return end != null;
    }

    @Override
    public int compareTo(FlowBuilder o) {
        return getStartPosition() - o.getStartPosition();
    }

    public Flow build() {
        if (!filled())
            return null;

        Collection<Flow> subCalls = new ArrayList<Flow>();
        for (FlowBuilder subCall : subEntries) {
            subCalls.add(subCall.build());
        }

        return new Flow(start, end, subCalls);
    }

    @Override
    public String toString() {
        return "CallBuilder [" + parent + ", " + getStartPosition() + " > " + getEndPosition() + "]";
    }

}
