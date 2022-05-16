package com.langrsoft.controller;

import java.util.Date;

public class CheckoutRequest {
    private String patronId;
    private String holdingBarcode;
    private Date checkoutDate;

    public CheckoutRequest() {
    }

    public CheckoutRequest(String patronId, String holdingBarcode, Date checkoutDate) {
        this.patronId = patronId;
        this.holdingBarcode = holdingBarcode;
        this.checkoutDate = checkoutDate;
    }

    public void setPatronId(String patronId) {
        this.patronId = patronId;
    }

    public String getPatronId() {
        return patronId;
    }

    public void setHoldingBarcode(String holdingBarcode) {
        this.holdingBarcode = holdingBarcode;
    }

    public String getHoldingBarcode() {
        return holdingBarcode;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }
}
