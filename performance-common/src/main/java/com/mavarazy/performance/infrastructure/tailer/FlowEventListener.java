package com.mavarazy.performance.infrastructure.tailer;

import com.mavarazy.performance.flow.event.FlowEvent;

public interface FlowEventListener {

    public void flowEventAdded(FlowEvent flowEvent);

}
