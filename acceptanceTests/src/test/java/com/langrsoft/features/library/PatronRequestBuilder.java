package com.langrsoft.features.library;

import com.langrsoft.controller.PatronRequest;

public class PatronRequestBuilder {
    private String name = "x";
    private String id;
    private int fineBalance = 0;

    public PatronRequestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PatronRequestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PatronRequestBuilder fineBalance(int fineBalance) {
        this.fineBalance = fineBalance;
        return this;
    }

    public PatronRequest build() {
        var request = new PatronRequest();
        request.setId(id);
        request.setName(name);
        request.setFineBalance(fineBalance);
        return request;
    }
}
