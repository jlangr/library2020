package com.loc.material.api;

public class MaterialNotFoundException extends RuntimeException {
    public MaterialNotFoundException(NullPointerException exception) {
        super(exception);
    }
}
