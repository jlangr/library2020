package com.langrsoft.features.library;

import com.langrsoft.controller.HoldingResponse;

import java.util.Date;

public class HoldingResponseBuilder {

    private String author;
    private String title;
    private String year;
    private String format;
    private Date dateDue;
    private String barcode;
    private Integer copyNumber;
    private Date dateCheckedOut;
    private Date dateLastCheckedIn;
    private boolean isAvailable;

    public HoldingResponseBuilder author(String author) {
        this.author = author;
        return this;
    }

    public HoldingResponseBuilder title(String title) {
        this.title = title;
        return this;
    }

    public HoldingResponseBuilder year(String year) {
        this.year = year;
        return this;
    }

    public HoldingResponseBuilder format(String format) {
        this.format = format;
        return this;
    }

    public HoldingResponseBuilder dateDue(Date dateDue) {
        this.dateDue = dateDue;
        return this;
    }

    public HoldingResponseBuilder barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public HoldingResponseBuilder copyNumber(Integer copyNumber) {
        this.copyNumber = copyNumber;
        return this;
    }

    public HoldingResponseBuilder dateCheckedOut(Date dateCheckedOut) {
        this.dateCheckedOut = dateCheckedOut;
        return this;
    }

    public HoldingResponseBuilder dateLastCheckedIn(Date dateLastCheckedIn) {
        this.dateLastCheckedIn = dateLastCheckedIn;
        return this;
    }

    public HoldingResponseBuilder isAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public HoldingResponse build() {
        var request = new HoldingResponse();
        request.setAuthor(author);
        request.setBarcode(barcode);
        request.setCopyNumber(copyNumber);
        request.setDateCheckedOut(dateCheckedOut);
        request.setDateDue(dateDue);
        request.setDateLastCheckedIn(dateLastCheckedIn);
        request.setFormat(format);
        request.setIsAvailable(isAvailable);
        request.setTitle(title);
        request.setYear(year);
        return request;
    }
}
