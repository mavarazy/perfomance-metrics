package com.mavarazy.performance.flow.call.metric;

import com.mavarazy.performance.flow.call.CallMetric;

public class CallResultMetric extends CallMetric {

    /**
     * Generated 27/10/2012
     */
    private static final long serialVersionUID = -4192164338656484189L;

    final private Object result;

    public CallResultMetric(final Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
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
        CallResultMetric other = (CallResultMetric) obj;
        if (result == null) {
            if (other.result != null) {
                return false;
            }
        } else if (!result.equals(other.result)) {
            return false;
        }
        return true;
    }
}
