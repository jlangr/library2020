package utils;

import java.lang.reflect.Array;
import java.util.List;

public class ArrayUtils {
    public static <T> T[] asArray(List<T> list, Class<T> type) {
        T[] array = (T[]) Array.newInstance(type, list.size());
        list.toArray(array);
        return array;
    }
}
