package com.mavarazy.performance.infrastructure.repository.file;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.tailer.FlowEventListener;

public class TestFlowEventListener implements FlowEventListener {

    final private BlockingQueue<FlowEvent> flowEvents = new LinkedBlockingQueue<FlowEvent>();

    @Override
    public void flowEventAdded(FlowEvent flowEvent) {
        try {
            getFlowEvents().put(flowEvent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public FlowEvent poll() {
        try {
            return getFlowEvents().poll(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public BlockingQueue<FlowEvent> getFlowEvents() {
        return flowEvents;
    }

}
