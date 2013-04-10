package com.mavarazy.performance.flow.call.event;

import java.lang.reflect.Method;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.call.CallEvent;

public class CallEndEvent extends CallEvent {
    /**
     * Generated 21/10/2012
     */
    private static final long serialVersionUID = 1959628276433665426L;

    final private Object result;

    public CallEndEvent(final Invocation callInvocation) {
        this(callInvocation, null);
    }

    public CallEndEvent(final Method method) {
        this(Invocation.valueOf(method), null);
    }

    public CallEndEvent(final Method method, final Object result) {
        this(Invocation.valueOf(method), result);
    }

    public CallEndEvent(final Invocation invocation, final Object result) {
        super(invocation);
        this.result = result;
    }
    
    public Object getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        CallEndEvent other = (CallEndEvent) obj;
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
