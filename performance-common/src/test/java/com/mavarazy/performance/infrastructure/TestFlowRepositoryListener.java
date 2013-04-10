package com.mavarazy.performance.infrastructure;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mavarazy.performance.flow.Flow;

public class TestFlowRepositoryListener implements FlowRepositoryListener {
    private BlockingQueue<Flow> addedFlows = new LinkedBlockingQueue<Flow>();

    @Override
    public void flowAdded(Flow flow) {
        addedFlows.add(flow);
    }

    public Collection<Flow> getAddedFlows() {
        return addedFlows;
    }
    
    public Flow poll() {
        try {
            return addedFlows.poll(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }
}
