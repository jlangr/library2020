package com.langrsoft.external;

public class MaterialNotFoundException extends RuntimeException {
    public MaterialNotFoundException(NullPointerException exception) {
        super(exception);
    }
}
