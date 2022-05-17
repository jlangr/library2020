package com.langrsoft.controller;

public class AddHoldingRequest {
    private String sourceId;
    private String branchScanCode;

    public AddHoldingRequest() {
    }

    public AddHoldingRequest(String sourceId, String branchScanCode) {
        this.sourceId = sourceId;
        this.branchScanCode = branchScanCode;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getBranchScanCode() {
        return branchScanCode;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setBranchScanCode(String branchScanCode) {
        this.branchScanCode = branchScanCode;
    }
}
