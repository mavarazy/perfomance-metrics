package com.mavarazy.performance.flow.group;

import java.util.ArrayList;
import java.util.Collection;

import com.mavarazy.performance.flow.Flow;
import com.mavarazy.performance.infrastructure.FlowRepositoryListener;

public class FlowGroupObserver implements FlowRepositoryListener {

    final private Collection<FlowGroupManager> groupManagers = new ArrayList<FlowGroupManager>();

    {
        groupManagers.add(new InvocationGroupManager());
    }

    @Override
    public void flowAdded(Flow newFlow) {
        for (FlowGroupManager groupManager : groupManagers) {
            groupManager.update(newFlow);
        }
    }

}
