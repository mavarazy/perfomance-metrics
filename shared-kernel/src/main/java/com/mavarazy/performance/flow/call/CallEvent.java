package com.mavarazy.performance.flow.call;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.mavarazy.performance.Invocation;

abstract public class CallEvent implements Serializable {

    /**
     * Generated 21/10/2012
     */
    private static final long serialVersionUID = -3948942301982420997L;

    final private Invocation callInvocation;

    public CallEvent(final Invocation invocation) {
        this.callInvocation = invocation;
    }

    public CallEvent(final Method method) {
        this.callInvocation = Invocation.valueOf(method);
    }

    public Invocation getInvocation() {
        return callInvocation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callInvocation == null) ? 0 : callInvocation.hashCode());
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
        CallEvent other = (CallEvent) obj;
        if (callInvocation == null) {
            if (other.callInvocation != null) {
                return false;
            }
        } else if (!callInvocation.equals(other.callInvocation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + callInvocation + "]";
    }

}
