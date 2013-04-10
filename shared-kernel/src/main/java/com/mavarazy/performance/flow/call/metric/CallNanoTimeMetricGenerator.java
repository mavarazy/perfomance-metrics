package com.mavarazy.performance.flow.call.metric;

import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.call.CallMetricGenerator;

public class CallNanoTimeMetricGenerator implements CallMetricGenerator {

    @Override
    public CallMetric generate(CallEvent callEvent) {
        return new CallNanoTimeMetric(System.nanoTime());
    }

}
