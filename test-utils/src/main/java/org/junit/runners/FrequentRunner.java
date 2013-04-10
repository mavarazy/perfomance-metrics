package org.junit.runners;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class FrequentRunner extends BlockJUnit4ClassRunner {

    final private int runs;
    final private static int DEFAULT_RUNS = 1;

    public FrequentRunner(Class<?> klass) throws InitializationError {
        super(klass);
        RunTimes annotation = klass.getAnnotation(RunTimes.class);
        runs = annotation != null ? Math.max(annotation.value(), DEFAULT_RUNS) : DEFAULT_RUNS;
    }

    @Override
    public void run(final RunNotifier notifier) {
        for (int i = 0; i < runs; i++)
            super.run(notifier);
    }
}
