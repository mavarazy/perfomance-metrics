package com.mavarazy.performance.flow.call.metric;

import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.call.CallMetricGenerator;
import com.mavarazy.performance.flow.call.event.CallEndEvent;

public class CallResultMetricGenerator implements CallMetricGenerator {

    @Override
    public CallMetric generate(CallEvent callEvent) {
        CallMetric callMetric = null;

        if (callEvent instanceof CallEndEvent)
            callMetric = new CallResultMetric(((CallEndEvent) callEvent).getResult());

        return callMetric;
    }

    @Override
    public Object clone() {
        return this;
    }

}
