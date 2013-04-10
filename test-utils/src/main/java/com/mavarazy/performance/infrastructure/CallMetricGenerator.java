package com.mavarazy.performance.infrastructure;


import java.util.Random;

import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.call.metric.CallArgumentsMetric;
import com.mavarazy.performance.flow.call.metric.CallNanoTimeMetric;
import com.mavarazy.performance.flow.call.metric.CallResultMetric;
import com.mavarazy.performance.flow.call.metric.CallTimeMetric;

public class CallMetricGenerator {

    final static private Object[] DEFAULT_ARGUMENTS = new String[] { "argument 1", "argument 2", "argument 3" };
    final static private Object DEFAULT_RESULT = "result";

    final static private Random random = new Random();

    static public CallMetric random() {
        int selection = random.nextInt(4);

        switch (selection) {
        case 0:
            return new CallArgumentsMetric(DEFAULT_ARGUMENTS);
        case 1:
            return new CallNanoTimeMetric(System.nanoTime());
        case 2:
            return new CallTimeMetric(System.currentTimeMillis());
        case 3:
            return new CallResultMetric(DEFAULT_RESULT);
        }

        return null;
    }

}