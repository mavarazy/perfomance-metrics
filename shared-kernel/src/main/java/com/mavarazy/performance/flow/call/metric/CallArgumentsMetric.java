package com.mavarazy.performance.flow.call.metric;

import java.util.Arrays;

import com.mavarazy.performance.flow.call.CallMetric;

public class CallArgumentsMetric extends CallMetric {

    /**
     * Generated 27/10/2012
     */
    private static final long serialVersionUID = -1535820239323530908L;

    final private Object[] arguments;

    public CallArgumentsMetric(String argumentPresentation) {
        this.arguments = new Object[] { argumentPresentation };
    }

    public CallArgumentsMetric(final Object[] arguments) {
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arguments);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CallArgumentsMetric other = (CallArgumentsMetric) obj;
        if (!Arrays.equals(arguments, other.arguments)) {
            return false;
        }
        return true;
    }
}
