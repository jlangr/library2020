package com.langrsoft.domain;

import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.ClassificationService;

public class ClassificationApiFactory {
    private static final ClassificationApi defaultService = new ClassificationService();
    private static ClassificationApi service = defaultService;

    private ClassificationApiFactory() {}

    public static void setService(ClassificationApi service) {
        ClassificationApiFactory.service = service;
    }

    public static ClassificationApi getService() {
        return service;
    }
}
