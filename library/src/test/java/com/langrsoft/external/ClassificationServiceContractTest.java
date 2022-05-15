package com.langrsoft.external;

public class ClassificationServiceContractTest extends ClassificationApiContract {
    @Override
    protected ClassificationApi createClassificationApiImpl() {
        return new ClassificationService();
    }

    @Override
    protected String validQueryIsbn() {
        return "0-307-26543-9";
    }
}
