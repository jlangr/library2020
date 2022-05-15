package com.langrsoft.util;

import java.util.*;

public class MultiMap<K, V> {
    private Map<K, List<V>> data = new HashMap<>();

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return data.size();
    }

    public int valuesSize() {
        return values().size();
    }

    public void put(K key, V value) {
        if (key == null)
            throw new NullPointerException();
        var list = get(key);
        if (list == null) {
            list = new ArrayList<>();
            data.put(key, list);
        }
        list.add(value);
    }

    public List<V> get(Object key) {
        return data.get(key);
    }

    public Collection<V> values() {
        List<V> results = new ArrayList<>();
        for (Iterator<List<V>> it = data.values().iterator(); it.hasNext(); ) {
            List<V> list = it.next();
            results.addAll(list);
        }
        return results;
    }

    public void clear() {
        data.clear();
    }
}
