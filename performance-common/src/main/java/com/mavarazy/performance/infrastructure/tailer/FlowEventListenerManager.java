package com.mavarazy.performance.infrastructure.tailer;

import java.util.HashSet;
import java.util.Set;

import com.mavarazy.performance.flow.event.FlowEvent;

public class FlowEventListenerManager {

    final private Object syncObject = new Object();
    private Set<FlowEventListener> flowEventListeners = new HashSet<FlowEventListener>();

    public synchronized void addFlowEventListener(FlowEventListener eventListener) {
        if (eventListener == null || flowEventListeners.contains(eventListener))
            return;
        synchronized (syncObject) {

            Set<FlowEventListener> newFlowEventListeners = new HashSet<FlowEventListener>();
            newFlowEventListeners.addAll(flowEventListeners);
            newFlowEventListeners.add(eventListener);

            flowEventListeners = newFlowEventListeners;
        }
    }

    public void publish(FlowEvent newEvent) {
        for (FlowEventListener flowEventListener : flowEventListeners) {
            flowEventListener.flowEventAdded(newEvent);
        }
    }
}
