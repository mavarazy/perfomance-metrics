package com.mavarazy.performance.flow.call.event;

import java.lang.reflect.Method;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.call.CallEvent;

public class CallErrorEvent extends CallEvent {
    /**
     * Generated 21/10/2012
     */
    private static final long serialVersionUID = -3260428266340333144L;

    final private String errorMessage;

    public CallErrorEvent(final Method method, final Error error) {
        this(Invocation.valueOf(method), error);
    }

    public CallErrorEvent(final Invocation callInvocation, final Error error) {
        this(callInvocation, (error != null ? error.getClass().getSimpleName() + ":" + error.getMessage() : "unknown"));
    }
    
    public CallErrorEvent(final Invocation callInvocation, final String errorMessage) {
        super(callInvocation);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
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
        CallErrorEvent other = (CallErrorEvent) obj;
        if (errorMessage == null) {
            if (other.errorMessage != null) {
                return false;
            }
        } else if (!errorMessage.equals(other.errorMessage)) {
            return false;
        }
        return true;
    }

}
