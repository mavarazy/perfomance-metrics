package com.mavarazy.performance.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.builder.FlowConstructor;
import com.mavarazy.performance.infrastructure.tailer.FlowEventListener;

public class FlowRepositoryImpl implements FlowRepository, FlowEventListener {

    final private FlowRepositoryListenerManager listenerManager = new FlowRepositoryListenerManager();
    final private BlockingQueue<FlowEvent> unprocessedEvents = new LinkedBlockingQueue<FlowEvent>();

    public FlowRepositoryImpl() {
        new Thread(new EventProcessor(unprocessedEvents, listenerManager), "FlowEvent processor").start();
    }

    @Override
    public void addListener(FlowRepositoryListener flowRepositoryListener) {
        this.listenerManager.addListener(flowRepositoryListener);
    }

    @Override
    public void flowEventAdded(FlowEvent flowEvent) {
        try {
            unprocessedEvents.put(flowEvent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class EventProcessor implements Runnable {
        final private Map<String, FlowConstructor> pendingFlows = new HashMap<String, FlowConstructor>();
        final private BlockingQueue<FlowEvent> flowEvents;
        final private FlowRepositoryListenerManager listenerManage;

        public EventProcessor(final BlockingQueue<FlowEvent> events, final FlowRepositoryListenerManager listenerManage) {
            this.flowEvents = events;
            this.listenerManage = listenerManage;
        }

        @Override
        public void run() {
            while (true) {
                FlowEvent flowEvent = null;
                try {
                    flowEvent = flowEvents.take();
                } catch (InterruptedException e) {
                    return;
                }
                // Step 1. Extracting flow identifier from event
                String flowIdentifier = flowEvent.getFlowPosition().getFlowIdentifier();
                // Step 2. Ensuring there is already flow builder for this event
                if (pendingFlows.get(flowIdentifier) == null) {
                    pendingFlows.put(flowIdentifier, FlowConstructor.create(flowIdentifier));
                }
                // Step 3. Checking if flow is complete and updating listeners
                if (pendingFlows.get(flowIdentifier).add(flowEvent)) {
                    listenerManage.callAdded(pendingFlows.remove(flowIdentifier).build());
                }
            }
        }

    }

}
