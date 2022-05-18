package com.langrsoft.features.library;

import com.langrsoft.controller.BranchRequest;

public class BranchRequestBuilder {
    private String name = "x";
    private String id;

    public BranchRequestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public BranchRequestBuilder id(String id) {
        this.id = id;
        return this;
    }

    public BranchRequest build() {
        var request = new BranchRequest();
        request.setId(id);
        request.setName(name);
        return request;
    }
}
