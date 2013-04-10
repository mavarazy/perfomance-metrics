package com.mavarazy.performance.flow;

import java.util.Collection;

import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.repository.FlowEventRepository;
import com.mavarazy.performance.infrastructure.repository.FlowEventRepositoryFactory;

public class FlowContext {

    final private FlowState flowState;
    final private FlowSpecification callSpecification;
    final private FlowEventRepository flowEventRepository;

    private FlowContext(FlowSpecification callSpecification) {
        this.callSpecification = callSpecification;
        this.flowEventRepository = FlowEventRepositoryFactory.createRepository(callSpecification.getStoreSpecification());
        this.flowState = new FlowState();
    }

    public static FlowContext create(FlowSpecification callSpecification) {
        assert callSpecification != null : "Call specification can't be NULL";
        return new FlowContext(callSpecification);
    }

    public void process(CallEvent callEvent) {
        // Step 1. Update current state
        flowState.update(callEvent);
        // Step 2. Check if this event must be processed according to current specification
        if (callSpecification.needTracking(flowState)) {
            Collection<CallMetric> callMetrics = callSpecification.generateMetrics(callEvent);
            FlowPosition flowIdentifier = flowState.nextIdentifier();
            FlowEvent flowEvent = new FlowEvent(flowIdentifier, callEvent, callMetrics);
            flowEventRepository.save(flowEvent);
        }
    }

}
