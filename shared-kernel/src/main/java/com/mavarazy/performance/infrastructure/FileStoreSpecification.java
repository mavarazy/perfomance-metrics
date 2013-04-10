package com.mavarazy.performance.infrastructure;

import java.io.File;

import com.mavarazy.performance.flow.event.FlowEvent;

public class FileStoreSpecification extends StoreSpecification {

    final static private int DEFAULT_BUFFER_SIZE = 8 * 1024;
    final static private boolean DEFAULT_FLUSH = false;

    final private File file;
    final private PresentationStream<String, FlowEvent> presentationStream;
    final private boolean flush;
    final private int bufferSize;

    public FileStoreSpecification(String fileName, PresentationStream<String, FlowEvent> presentationStream) {
        this(fileName, presentationStream, DEFAULT_FLUSH, DEFAULT_BUFFER_SIZE);
    }

    public FileStoreSpecification(String fileName, PresentationStream<String, FlowEvent> presentationStream, boolean flush) {
        this(fileName, presentationStream, flush, DEFAULT_BUFFER_SIZE);
    }

    public FileStoreSpecification(String fileName, PresentationStream<String, FlowEvent> presentationStream, boolean flush, int bufferSize) {
        this.file = new File(fileName);
        this.presentationStream = presentationStream;
        this.flush = flush;
        this.bufferSize = bufferSize;
    }

    public PresentationStream<String, FlowEvent> getPresentationStream() {
        return presentationStream;
    }

    public File getFile() {
        return file;
    }

    public boolean isFlush() {
        return flush;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    @Override
    public void clean() {
        if (file.exists()) {
            boolean removed = false;
            try {
                removed = file.delete();
            } catch (SecurityException throwable) {
                throwable.printStackTrace();
            } finally {
                if (!removed) {
                    file.deleteOnExit();
                }
            }
        }
    }
}
