package com.mavarazy.performance.infrastructure.repository.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.FileStoreSpecification;
import com.mavarazy.performance.infrastructure.repository.FlowEventRepository;

public class FileFlowEventRepository implements FlowEventRepository {

    final private FileStoreSpecification fileStoreStrategy;
    private BufferedWriter bufferedWriter;

    public FileFlowEventRepository(FileStoreSpecification fileStoreStrategy) {
        this.fileStoreStrategy = fileStoreStrategy;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileStoreStrategy.getFile(), true), fileStoreStrategy.getBufferSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(FlowEvent call) {
        if (call == null || bufferedWriter == null)
            return;

        String savedValue = fileStoreStrategy.getPresentationStream().write(call);

        // Save to buffer
        try {
            bufferedWriter.write(savedValue.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // Flush input wright away
        if (fileStoreStrategy.isFlush()) {
            try {
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
