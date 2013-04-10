package com.mavarazy.performance.flow.call.metric;

import com.mavarazy.performance.flow.call.CallMetric;

public class CallTimeMetric extends CallMetric {

    /**
     * Generated 27/10/2012
     */
    private static final long serialVersionUID = 3804902477039587119L;

    final private long time;

    public CallTimeMetric(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (time ^ (time >>> 32));
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
        CallTimeMetric other = (CallTimeMetric) obj;
        if (time != other.time) {
            return false;
        }
        return true;
    }

}