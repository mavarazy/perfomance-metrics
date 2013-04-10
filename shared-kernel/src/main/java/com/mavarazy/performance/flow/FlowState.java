package com.mavarazy.performance.flow;

import java.util.UUID;

import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.event.CallEndEvent;
import com.mavarazy.performance.flow.call.event.CallStartEvent;

public class FlowState {

    private String flowIdentifier;
    private int currentPosition;
    private int currentDepth;

    public FlowState() {
    }

    public void update(CallEvent callEvent) {
        // Step 1. Update current depth of the call
        if (callEvent instanceof CallStartEvent) {
            if (currentDepth == 0) {
                flowIdentifier = UUID.randomUUID().toString();
                currentPosition = 0;
            }
            currentDepth++;
        } else if (callEvent instanceof CallEndEvent) {
            currentDepth--;
        }
    }

    public FlowPosition nextIdentifier() {
        return new FlowPosition(flowIdentifier, currentPosition++);
    }

    public String getFlowIdentifier() {
        return flowIdentifier;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getCurrentDepth() {
        return currentDepth;
    }
}
