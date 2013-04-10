package com.mavarazy.performance.infrastructure.tailer.file;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.FileStoreSpecification;
import com.mavarazy.performance.infrastructure.tailer.FlowEventListener;
import com.mavarazy.performance.infrastructure.tailer.FlowEventListenerManager;

public class FileTailer implements FlowEventTailer {

    final private FileStoreSpecification fileStoreStrategy;
    final private FlowEventListenerManager flowEventListenerManager;

    final private Tailer tailer;
    final private TailerListener listener = new TailerListenerAdapter() {
        @Override
        public void handle(String newMessage) {
            FlowEvent newFlowEvent = fileStoreStrategy.getPresentationStream().read(newMessage);
            flowEventListenerManager.publish(newFlowEvent);
        }
    };

    public FileTailer(FileStoreSpecification fileStoreStrategy, FlowEventListenerManager eventListenerManager) {
        this.fileStoreStrategy = fileStoreStrategy;
        this.flowEventListenerManager = eventListenerManager;

        this.tailer = Tailer.create(fileStoreStrategy.getFile(), listener);
    }

    public FlowEventListenerManager getFlowEventListenerManager() {
        return flowEventListenerManager;
    }

    @Override
    public FileStoreSpecification getStoreSpecification() {
        return fileStoreStrategy;
    }

    @Override
    public void stop() {
        tailer.stop();
    }

    @Override
    public void addListener(FlowEventListener flowEventListener) {
        flowEventListenerManager.addFlowEventListener(flowEventListener);
    }
}
