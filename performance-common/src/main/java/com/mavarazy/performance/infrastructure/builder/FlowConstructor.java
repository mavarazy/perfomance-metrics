package com.mavarazy.performance.infrastructure.builder;

import com.mavarazy.performance.flow.Flow;
import com.mavarazy.performance.flow.event.FlowEvent;

public abstract class FlowConstructor {

    abstract public String getIdentifier();

    abstract public boolean add(FlowEvent call);

    abstract public boolean complete();

    abstract public Flow build();

    final static public FlowConstructor create(String identifier) {
        return new SimpleFlowConstructor(identifier);
    }

}
