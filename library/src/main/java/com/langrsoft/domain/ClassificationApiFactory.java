package com.langrsoft.domain;

import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.ClassificationService;

public class ClassificationApiFactory {
    private static final ClassificationApi DefaultService = new ClassificationService();
    private static ClassificationApi Service = DefaultService;

    public static void setService(ClassificationApi service) {
        Service = service;
    }

    public static ClassificationApi getService() {
        return Service;
    }
}
