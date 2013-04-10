package com.mavarazy.performance.flow.call;

import java.io.Serializable;

abstract public class CallMetric implements Serializable {

    /**
     * Generated 27/10/2012
     */
    private static final long serialVersionUID = 7501641358192396810L;

    @Override
    abstract public boolean equals(Object other);

    @Override
    abstract public int hashCode();
}
