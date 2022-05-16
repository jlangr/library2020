package com.langrsoft.controller;

import java.util.Date;

public class CheckinRequest {
    private String holdingBarcode;
    private Date checkinDate;
    private String branchScanCode;

    public CheckinRequest() {
    }

    public CheckinRequest(String holdingBarcode, Date checkinDate, String branchScanCode) {
        this.holdingBarcode = holdingBarcode;
        this.checkinDate = checkinDate;
        this.branchScanCode = branchScanCode;
    }

    public void setHoldingBarcode(String holdingBarcode) {
        this.holdingBarcode = holdingBarcode;
    }

    public String getHoldingBarcode() {
        return holdingBarcode;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setBranchScanCode(String branchScanCode) {
        this.branchScanCode = branchScanCode;
    }

    public String getBranchScanCode() {
        return branchScanCode;
    }
}