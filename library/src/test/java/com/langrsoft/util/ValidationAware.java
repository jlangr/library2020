package com.langrsoft.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationAware {
    Map<String, List<String>> fieldErrors = new HashMap<>();

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    public Map<String, List<String>> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String key, String value) {
        fieldErrors.put(key, List.of(value));
    }
}
