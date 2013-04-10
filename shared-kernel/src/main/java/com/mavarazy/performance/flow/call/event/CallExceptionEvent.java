package com.mavarazy.performance.flow.call.event;

import java.lang.reflect.Method;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.call.CallEvent;

public class CallExceptionEvent extends CallEvent {
    /**
     * Generated 21/10/2012
     */
    private static final long serialVersionUID = -3529049583493533534L;

    final private String exceptionMessage;

    public CallExceptionEvent(final Method method, final Throwable throwable) {
        this(Invocation.valueOf(method), throwable);
    }

    public CallExceptionEvent(final Invocation call, final Throwable throwable) {
        this(call, throwable != null ? throwable.getClass().getSimpleName() + ":" + throwable.getMessage() : "unknown");
    }

    public CallExceptionEvent(final Invocation callInvocation, final String exceptionMessage) {
        super(callInvocation);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((exceptionMessage == null) ? 0 : exceptionMessage.hashCode());
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
        CallExceptionEvent other = (CallExceptionEvent) obj;
        if (exceptionMessage == null) {
            if (other.exceptionMessage != null) {
                return false;
            }
        } else if (!exceptionMessage.equals(other.exceptionMessage)) {
            return false;
        }
        return true;
    }

}
