package com.mavarazy.performance.infrastructure.repository;

import com.mavarazy.performance.flow.event.FlowEvent;

public interface FlowEventRepository {

    public void save(FlowEvent call);

}
