package com.langrsoft.util;

import java.util.Map;

public class Properties {
    private static final Map<String,String> props = Map.of("foo", "Foo Range Message");

    public static String getProperty(String key) {
        return props.get(key);
    }
}
