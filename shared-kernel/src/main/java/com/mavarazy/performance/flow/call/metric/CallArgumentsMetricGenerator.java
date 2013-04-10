package com.mavarazy.performance.flow.call.metric;

import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.call.CallMetricGenerator;
import com.mavarazy.performance.flow.call.event.CallStartEvent;

public class CallArgumentsMetricGenerator implements CallMetricGenerator {

    @Override
    public CallMetric generate(CallEvent callEvent) {
        CallMetric callMetric = null;

        if (callEvent instanceof CallStartEvent)
            callMetric = new CallArgumentsMetric(((CallStartEvent) callEvent).getArguments());

        return callMetric;
    }
    
    @Override
    public Object clone() {
        return this;
    }

}
