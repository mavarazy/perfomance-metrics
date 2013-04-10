package com.mavarazy.performance.infrastructure.repository.file;

import org.junit.runners.RunTimes;

import com.mavarazy.performance.infrastructure.FileStoreSpecification;
import com.mavarazy.performance.infrastructure.presentation.csv.CSVFlowEventPresentationStream;

@RunTimes(1)
public class FileFlowEventRepositoryToTailerTest extends AbstractFlowEventRepositoryToTailerTest {

    public FileFlowEventRepositoryToTailerTest() {
        super(new FileStoreSpecification("test.log", new CSVFlowEventPresentationStream(), true));
    }

}
