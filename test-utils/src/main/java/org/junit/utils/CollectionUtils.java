package org.junit.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CollectionUtils {

    private CollectionUtils() {
        throw new IllegalAccessError();
    }

    final private static Random random = new Random();

    final public static <T> List<Collection<T>> split(Collection<T> sourceCollection, int size) {
        List<Collection<T>> resultCollection = new ArrayList<Collection<T>>();

        for (int i = 0; i < size; i++)
            resultCollection.add(new ArrayList<T>());

        for (T source : sourceCollection) {
            resultCollection.get(random.nextInt(size)).add(source);
        }

        return resultCollection;
    }

    final public static <T> Collection<T> nonMatched(Collection<T> tested, Collection<T> sources) {
        Collection<T> nonMatched = new ArrayList<T>();
        for (T source : sources) {
            if (!tested.contains(source)) {
                nonMatched.add(source);
            }
        }
        return nonMatched;
    }

}
