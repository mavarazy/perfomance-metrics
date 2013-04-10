package com.mavarazy.performance.flow;

public class FlowPosition {

    final private String flowIdentifier;
    final private int position;

    public FlowPosition(final String identifier, final int position) {
        this.position = position;
        this.flowIdentifier = identifier;
    }

    public int getPosition() {
        return position;
    }

    public String getFlowIdentifier() {
        return flowIdentifier;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flowIdentifier == null) ? 0 : flowIdentifier.hashCode());
        result = prime * result + position;
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
        FlowPosition other = (FlowPosition) obj;
        if (flowIdentifier == null) {
            if (other.flowIdentifier != null) {
                return false;
            }
        } else if (!flowIdentifier.equals(other.flowIdentifier)) {
            return false;
        }
        if (position != other.position) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Flow [" + flowIdentifier + " / " + position + "]";
    }
}
