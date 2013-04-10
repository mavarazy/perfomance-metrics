package com.mavarazy.performance.flow.call.event;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.call.CallEvent;

public class CallStartEvent extends CallEvent {
    /**
     * Generated 21/10/2012
     */
    final static private long serialVersionUID = 5300160041180541299L;

    final private Object[] arguments;

    public CallStartEvent(final Invocation invocation) {
        this(invocation, null);
    }

    public CallStartEvent(final Method method) {
        this(method, null);
    }

    public CallStartEvent(final Method method, final Object[] arguments) {
        this(Invocation.valueOf(method), arguments);
    }

    public CallStartEvent(final Invocation callInvocation, final Object[] arguments) {
        super(callInvocation);
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(arguments);
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        CallStartEvent other = (CallStartEvent) obj;
        if (!Arrays.equals(arguments, other.arguments)) {
            return false;
        }
        return true;
    }

}
