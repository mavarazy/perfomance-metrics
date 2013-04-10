package com.mavarazy.performance.flow.event;

import java.util.Collection;

import com.mavarazy.performance.flow.FlowPosition;
import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;

public class FlowEvent {

    final private FlowPosition flowPosition;
    final private CallEvent callEvent;
    final private Collection<CallMetric> callMetrics;

    public FlowEvent(final FlowPosition flowPosition, final CallEvent callEvent, final Collection<CallMetric> callMetrics) {
        this.flowPosition = flowPosition;
        this.callEvent = callEvent;
        this.callMetrics = callMetrics;
    }

    public FlowPosition getFlowPosition() {
        return flowPosition;
    }

    public CallEvent getCallEvent() {
        return callEvent;
    }

    public Collection<CallMetric> getCallMetrics() {
        return callMetrics;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callEvent == null) ? 0 : callEvent.hashCode());
        result = prime * result + ((callMetrics == null) ? 0 : callMetrics.hashCode());
        result = prime * result + ((flowPosition == null) ? 0 : flowPosition.hashCode());
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
        FlowEvent other = (FlowEvent) obj;
        if (callEvent == null) {
            if (other.callEvent != null) {
                return false;
            }
        } else if (!callEvent.equals(other.callEvent)) {
            return false;
        }
        if (callMetrics == null) {
            if (other.callMetrics != null) {
                return false;
            }
        } else if (!callMetrics.equals(other.callMetrics)) {
            return false;
        }
        if (flowPosition == null) {
            if (other.flowPosition != null) {
                return false;
            }
        } else if (!flowPosition.equals(other.flowPosition)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FlowEvent [" + flowPosition + ", " + callEvent + ", " + callMetrics + "]";
    }
}
