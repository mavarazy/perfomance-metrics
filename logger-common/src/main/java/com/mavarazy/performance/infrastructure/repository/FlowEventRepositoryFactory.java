package com.mavarazy.performance.infrastructure.repository;

import com.mavarazy.performance.infrastructure.FileStoreSpecification;
import com.mavarazy.performance.infrastructure.StoreSpecification;
import com.mavarazy.performance.infrastructure.repository.file.FileFlowEventRepository;

public class FlowEventRepositoryFactory {

    public static FlowEventRepository createRepository(StoreSpecification storeSpecification) {
        if(storeSpecification instanceof FileStoreSpecification)
            return new FileFlowEventRepository((FileStoreSpecification) storeSpecification);
        throw new IllegalArgumentException();
    }
    
}
