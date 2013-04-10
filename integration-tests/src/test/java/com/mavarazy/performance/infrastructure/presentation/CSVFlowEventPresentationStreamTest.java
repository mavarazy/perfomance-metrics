package com.mavarazy.performance.infrastructure.presentation;

import org.junit.runner.RunWith;
import org.junit.runners.FrequentRunner;

import com.mavarazy.performance.infrastructure.presentation.csv.CSVFlowEventPresentationStream;

@RunWith(FrequentRunner.class)
public class CSVFlowEventPresentationStreamTest extends AbstractFlowEventPresentationStreamTest<String>{

    public CSVFlowEventPresentationStreamTest() {
        super(new CSVFlowEventPresentationStream());
    }

}
