package com.langrsoft.domain;

public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 1L;
}
