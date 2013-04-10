package com.mavarazy.performance.infrastructure.tailer.file;

import com.mavarazy.performance.infrastructure.StoreSpecification;
import com.mavarazy.performance.infrastructure.tailer.FlowEventListener;

public interface FlowEventTailer {

    public StoreSpecification getStoreSpecification();
    
    public void stop();
    
    public void addListener(FlowEventListener flowEventListener);

}
