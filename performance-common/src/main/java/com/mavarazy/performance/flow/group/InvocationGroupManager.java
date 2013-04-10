package com.mavarazy.performance.flow.group;

import java.util.HashMap;
import java.util.Map;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.Flow;

public class InvocationGroupManager implements FlowGroupManager {

    final private Map<Invocation, FlowGroup<Invocation>> existingGroups = new HashMap<Invocation, FlowGroup<Invocation>>();
    
    @Override
    public void update(Flow newFlow) {
        // Step 1. Ensure existence of the flow group
        Invocation invocation = newFlow.getInvocation();
        FlowGroup<Invocation> associatedGroup = existingGroups.get(invocation);
        if(associatedGroup == null) {
            associatedGroup = new FlowGroup<Invocation>(newFlow.getInvocation());
            existingGroups.put(invocation, associatedGroup);
        }
        // Step 2. Add new Flow to associated flowGroup
        associatedGroup.addFlow(newFlow);
        // Step 3. Repeat for all the sub flows
        for(Flow subFlow: newFlow.getSubFlows()) {
            update(subFlow);
        }
    }

}
