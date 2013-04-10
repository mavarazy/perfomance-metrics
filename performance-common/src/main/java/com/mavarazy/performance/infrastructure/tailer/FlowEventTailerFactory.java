package com.mavarazy.performance.infrastructure.tailer;

import com.mavarazy.performance.infrastructure.FileStoreSpecification;
import com.mavarazy.performance.infrastructure.StoreSpecification;
import com.mavarazy.performance.infrastructure.tailer.file.FileTailer;
import com.mavarazy.performance.infrastructure.tailer.file.FlowEventTailer;

public class FlowEventTailerFactory {

    final static private FlowEventListenerManager flowEventListenerManager = new FlowEventListenerManager();

    static public FlowEventTailer create(StoreSpecification storeSpecification) {
        if (storeSpecification instanceof FileStoreSpecification)
            return new FileTailer((FileStoreSpecification) storeSpecification, flowEventListenerManager);
        throw new IllegalArgumentException(String.valueOf(storeSpecification));
    }

    static public void addListener(FlowEventListener flowEventListener) {
        flowEventListenerManager.addFlowEventListener(flowEventListener);
    }
}
