package com.mavarazy.performance.infrastructure;

import java.util.HashSet;
import java.util.Set;

import com.mavarazy.performance.flow.Flow;

public class FlowRepositoryListenerManager implements FlowRepository {

    private Set<FlowRepositoryListener> repositoryListeners = new HashSet<FlowRepositoryListener>();

    @Override
    public void addListener(FlowRepositoryListener flowRepositoryListener) {
        if (flowRepositoryListener == null)
            return;

        Set<FlowRepositoryListener> newRepositoryListeners = new HashSet<FlowRepositoryListener>();
        newRepositoryListeners.addAll(repositoryListeners);
        newRepositoryListeners.add(flowRepositoryListener);

        this.repositoryListeners = newRepositoryListeners;
    }

    public void callAdded(Flow callFlow) {
        for (FlowRepositoryListener flowRepositoryListener : repositoryListeners) {
            flowRepositoryListener.flowAdded(callFlow);
        }
    }

}
