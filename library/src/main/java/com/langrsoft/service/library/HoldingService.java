package com.langrsoft.service.library;

import com.langrsoft.domain.*;
import com.langrsoft.external.Material;
import com.langrsoft.persistence.PatronStore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HoldingService {
    private final Catalog catalog = new Catalog();

    public String add(String sourceId, String branchId) {
        var branch = findBranch(branchId);
        var holding = new Holding(retrieveMaterialDetails(sourceId), branch);
        catalog.add(holding);
        return holding.getBarcode();
    }

    private Material retrieveMaterialDetails(String sourceId) {
        var material =
                ClassificationApiFactory.getService().retrieveMaterial(sourceId);
        if (material == null)
            throw new InvalidSourceIdException("cannot retrieve material with source ID " + sourceId);
        return material;
    }

    private Branch findBranch(String branchId) {
        var branch = new BranchService().find(branchId);
        if (branch == null)
            throw new BranchNotFoundException("Branch not found: " + branchId);
        return branch;
    }

    public boolean isAvailable(String barCode) {
        var holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.isAvailable();
    }

    public HoldingMap allHoldings() {
        var stack = new HoldingMap();
        for (var holding : catalog)
            stack.add(holding);
        return stack;
    }

    public Holding find(String barCode) {
        return catalog.find(barCode);
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return catalog.findByBranch(branchScanCode);
    }

    public void transfer(String barcode, String branchScanCode) {
        Holding holding = find(barcode);
        if (holding == null)
            throw new HoldingNotFoundException();
        Branch branch = new BranchService().find(branchScanCode);
        holding.transfer(branch);
    }

    public Date dateDue(String barCode) {
        Holding holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.dateDue();
    }

    public void checkOut(String patronId, String barCode, Date date) {
        Holding holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        if (!holding.isAvailable())
            throw new HoldingAlreadyCheckedOutException();
        holding.checkOut(date);

        PatronStore patronAccess = new PatronStore();
        Patron patron = patronAccess.find(patronId);
        patronAccess.addHoldingToPatron(patron, holding);
    }

    @SuppressWarnings("all") // remove warning suppression once refactored
    // Note that this method is missing some coverage, which is typical
    // for a method of this length
    public int checkIn(String barCode, Date date, String branchScanCode) {
        Branch branch = new BranchService().find(branchScanCode);
        Holding hld = find(barCode);
        if (hld == null)
            throw new HoldingNotFoundException();

        // set the holding to returned status
        HoldingMap holdings = null;
        hld.checkIn(date, branch);

        // locate the patron with the checked out book
        // could introduce a patron reference ID in the holding...
        Patron f = null;
        for (Patron p : new PatronService().allPatrons()) {
            holdings = p.holdingMap();
            for (Holding patHld : holdings) {
                if (hld.getBarcode().equals(patHld.getBarcode()))
                    f = p;
            }
        }

        // remove the book from the patron
        f.remove(hld);

        // check for late returns
        boolean isLate = false;
        Calendar c = Calendar.getInstance();
        c.setTime(hld.dateDue());
        int d = Calendar.DAY_OF_YEAR;

        // deal with leap year
        var leapDays = 0;
        if (c.getActualMaximum(d) == 366) {
            if (c.get(d) == 60) { // leap day
                leapDays = 1;
            }
        }

        if (hld.dateLastCheckedIn().after(c.getTime())) // is it late?
            isLate = true;

        if (isLate) {
            int daysLate = hld.daysLate(); // calculate # of days past due
            if (daysLate > 0)
                daysLate = daysLate - leapDays;
            int fineBasis = hld.getMaterial().getFormat().getDailyFine();
            switch (hld.getMaterial().getFormat()) {
                case BOOK:
                    f.addFine(fineBasis * daysLate);
                    break;
                case DVD:
                    int fine = Math.min(1000, 100 + fineBasis * daysLate);
                    f.addFine(fine);
                    break;
                case NEW_RELEASE_DVD:
                    f.addFine(fineBasis * daysLate);
                    break;
            }
            return daysLate;
        }
        return 0;
    }
}