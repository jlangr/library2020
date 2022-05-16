package domain.core;

import com.loc.material.api.Material;
import util.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class Holding {
    public static final String BARCODE_SEPARATOR = ":";
    private Material material;
    private Branch branch;
    private Date dateCheckedOut;
    private Date dateLastCheckedIn;
    private int copyNumber;

    public Holding(Material material) {
        this(material, Branch.CHECKED_OUT);
    }

    public Holding(Material material, Branch branch) {
        this(material, branch, 1);
    }

    public Holding(Material material, Branch branch, int copyNumber) {
        this.material = material;
        this.branch = branch;
        this.copyNumber = copyNumber;
    }

    public Material getMaterial() {
        return material;
    }

    public Branch getBranch() {
        return branch;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public String getBarcode() {
        return HoldingBarcode.createCode(material.getClassification(), copyNumber);
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public void transfer(Branch newBranch) {
        branch = newBranch;
    }

    public boolean isAvailable() {
        return branch != Branch.CHECKED_OUT;
    }

    public void checkOut(Date date) {
        branch = Branch.CHECKED_OUT;
        dateCheckedOut = date;
    }

    public Date dateCheckedOut() {
        return dateCheckedOut;
    }

    public Date dateDue() {
        if (dateCheckedOut == null) return null;
        return DateUtil.addDays(dateCheckedOut, getHoldingPeriod());
    }

    private int getHoldingPeriod() {
        return material.getFormat().getCheckoutPeriod();
    }

    public int checkIn(Date date, Branch branch) {
        this.dateLastCheckedIn = date;
        this.branch = branch;
        return daysLate();
    }


    public boolean isLate() {
        return daysLate() > 0;
    }

    public int daysLate() {
        return dateDue() == null
                ? 0
                : DateUtil.daysAfter(dateDue(), dateLastCheckedIn());
    }

    public Date dateLastCheckedIn() {
        return dateLastCheckedIn;
    }

    @Override
    public int hashCode() {
        return getBarcode().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (this.getClass() != object.getClass())
            return false;
        Holding that = (Holding) object;
        return this.getBarcode().equals(that.getBarcode());
    }

    @Override
    public String toString() {
        return material.toString() + "(" + copyNumber + ") @ " + branch.getName();
    }

    public int fine() {
        return calculateFine(material.getFineBasis(this));
    }

    public int calculateFine(int fineBasis) {
        switch (getMaterial().getFormat()) {
            case DVD:
                return Math.min(1000, 100 + fineBasis * daysLate());
            case Book:
            case NewReleaseDVD:
                return fineBasis * daysLate();
            default:
                return 0;
        }
    }
}