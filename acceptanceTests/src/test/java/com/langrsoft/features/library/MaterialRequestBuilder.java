package com.langrsoft.features.library;

import com.langrsoft.controller.MaterialRequest;

public class MaterialRequestBuilder {
    private String sourceId;
    private String classification = "QA-999";
    private String format = "Book";

    public MaterialRequest build() {
        MaterialRequest request = new MaterialRequest();
        request.setSourceId(sourceId);
        request.setClassification(classification);
        request.setFormat(format);
        return request;
    }

    public MaterialRequestBuilder sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public MaterialRequestBuilder classification(String classification) {
        this.classification = classification;
        return this;
    }

    public MaterialRequestBuilder format(String format) {
        this.format = format;
        return this;
    }
}
