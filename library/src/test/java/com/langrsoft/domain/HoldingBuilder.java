package com.langrsoft.domain;

import com.langrsoft.external.Material;

import java.util.Date;

public class HoldingBuilder {
    private Material material = new Material("1", "", "1", "", "");
    private Branch branch = Branch.CHECKED_OUT;
    private int copyNumber = 1;
    private Date checkoutDate = null;
    private String author;
    private String title;

    public HoldingBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public HoldingBuilder classification(String classification) {
        this.material.setClassification(classification);
        return this;
    }

    public HoldingBuilder checkout(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
        return this;
    }

    public HoldingBuilder branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public HoldingBuilder copyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
        return this;
    }

    public HoldingBuilder author(String author) {
        this.author = author;
        return this;
    }

    public HoldingBuilder title(String title) {
        this.title = title;
        return this;
    }

    public Holding build() {
        if (author != null)
            material.setAuthor(author);
        if (title != null)
            material.setTitle(title);
        var holding = new Holding(material, branch, copyNumber);
        if (checkoutDate != null)
            holding.checkOut(checkoutDate);
        return holding;
    }
}