package com.mavarazy.performance.flow.call.metric;

import com.mavarazy.performance.flow.call.CallMetric;

public class CallNanoTimeMetric extends CallMetric {

    /**
     * Generated 27/10/2012
     */
    private static final long serialVersionUID = 5478009310816564681L;

    final private long nanoTime;

    public CallNanoTimeMetric(final long nanoTime) {
        this.nanoTime = nanoTime;
    }

    public long getNanoTime() {
        return nanoTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (nanoTime ^ (nanoTime >>> 32));
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
        CallNanoTimeMetric other = (CallNanoTimeMetric) obj;
        if (nanoTime != other.nanoTime) {
            return false;
        }
        return true;
    }
}
