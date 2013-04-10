package com.mavarazy.performance.infrastructure;

public interface PresentationStream<R, P> {

    public interface PresentationWriter<T, S> {
        public S write(S accumulator, T object);
    }

    public interface PresentationReader<T, S> {
        public T read(S source);
    }

    public R write(P call);

    public P read(R result);
}
