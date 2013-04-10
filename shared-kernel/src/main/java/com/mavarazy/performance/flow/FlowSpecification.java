package com.mavarazy.performance.flow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.call.CallMetricGenerator;
import com.mavarazy.performance.flow.call.metric.CallArgumentsMetricGenerator;
import com.mavarazy.performance.flow.call.metric.CallNanoTimeMetricGenerator;
import com.mavarazy.performance.flow.call.metric.CallResultMetricGenerator;
import com.mavarazy.performance.flow.call.metric.CallTimeMetricGenerator;
import com.mavarazy.performance.infrastructure.StoreSpecification;

public class FlowSpecification {

    final public static int LOG_ALL = 0;

    final private int maxCallDepth;
    final private StoreSpecification storeSpecification;
    final private List<CallMetricGenerator> metricsGenerators = new ArrayList<CallMetricGenerator>();

    {
        metricsGenerators.add(new CallTimeMetricGenerator());
        metricsGenerators.add(new CallNanoTimeMetricGenerator());
        metricsGenerators.add(new CallArgumentsMetricGenerator());
        metricsGenerators.add(new CallResultMetricGenerator());
    }

    public FlowSpecification(final int callDepth, final StoreSpecification storeSpecification) {
        this.maxCallDepth = callDepth;
        this.storeSpecification = storeSpecification;
    }

    public boolean needTracking(FlowState currentState) {
        return maxCallDepth == LOG_ALL || currentState.getCurrentDepth() < maxCallDepth;
    }

    public Collection<CallMetric> generateMetrics(CallEvent callEvent) {
        Collection<CallMetric> callMetrics = new ArrayList<CallMetric>();
        for (CallMetricGenerator metricsGenerator : metricsGenerators) {
            CallMetric callMetric = metricsGenerator.generate(callEvent);
            if (callMetric != null)
                callMetrics.add(callMetric);
        }
        return callMetrics;
    }

    public StoreSpecification getStoreSpecification() {
        return storeSpecification;
    }

}
