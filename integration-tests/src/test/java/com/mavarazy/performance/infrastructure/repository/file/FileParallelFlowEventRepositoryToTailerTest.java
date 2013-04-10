package com.mavarazy.performance.infrastructure.repository.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.FileStoreSpecification;
import com.mavarazy.performance.infrastructure.PresentationStream;
import com.mavarazy.performance.infrastructure.StoreSpecification;
import com.mavarazy.performance.infrastructure.presentation.csv.CSVFlowEventPresentationStream;

public class FileParallelFlowEventRepositoryToTailerTest extends AbstractParallelFlowEventRepositoryToTailerTest {

    final private static Collection<StoreSpecification> storeSpecifications = new ArrayList<StoreSpecification>();
    static {
        PresentationStream<String, FlowEvent> presentationStream = new CSVFlowEventPresentationStream();
        for(int i = 0; i < 20; i++) {
            storeSpecifications.add(new FileStoreSpecification(UUID.randomUUID().toString() + ".log", presentationStream, true));
        }
    }
    
    public FileParallelFlowEventRepositoryToTailerTest() {
        super(storeSpecifications);
    }

}
