package com.mavarazy.performance.flow.call;

public interface CallMetricGenerator {

    public CallMetric generate(CallEvent callEvent);

}