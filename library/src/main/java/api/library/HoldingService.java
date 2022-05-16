package api.library;

import com.loc.material.api.Material;
import domain.core.*;
import persistence.PatronStore;

import java.util.Date;
import java.util.List;

public class HoldingService {
    private Catalog catalog = new Catalog();

    public String add(String sourceId, String branchId) {
        Branch branch = findBranch(branchId);
        Holding holding = new Holding(retrieveMaterialDetails(sourceId), branch);
        catalog.add(holding);
        return holding.getBarcode();
    }

    private Material retrieveMaterialDetails(String sourceId) {
        Material material =
                ClassificationApiFactory.getService().retrieveMaterial(sourceId);
        if (material == null)
            throw new InvalidSourceIdException("cannot retrieve material with source ID " + sourceId);
        return material;
    }

    private Branch findBranch(String branchId) {
        Branch branch = new BranchService().find(branchId);
        if (branch == null)
            throw new BranchNotFoundException("Branch not found: " + branchId);
        return branch;
    }

    public boolean isAvailable(String barCode) {
        Holding holding = findHolding(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.isAvailable();
    }

    public HoldingMap allHoldings() {
        HoldingMap stack = new HoldingMap();
        for (Holding holding : catalog)
            stack.add(holding);
        return stack;
    }

    public Holding findHolding(String barCode) {
        return catalog.find(barCode);
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return catalog.findByBranch(branchScanCode);
    }

    public void transfer(String barcode, String branchScanCode) {
        Holding holding = findHolding(barcode);
        if (holding == null)
            throw new HoldingNotFoundException();
        Branch branch = new BranchService().find(branchScanCode);
        holding.transfer(branch);
    }

    public Date dateDue(String barCode) {
        Holding holding = findHolding(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.dateDue();
    }

    public void checkOut(String patronId, String barCode, Date date) {
        var holding = findHolding(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        if (!holding.isAvailable())
            throw new HoldingAlreadyCheckedOutException();
        holding.checkOut(date);

        PatronStore patronAccess = new PatronStore();
        Patron patron = patronAccess.find(patronId);
        patronAccess.addHoldingToPatron(patron, holding);
    }

    public int checkIn(String barCode, Date date, String branchScanCode) {
        var holding = findHolding(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();

        var branch = new BranchService().find(branchScanCode);
        holding.checkIn(date, branch);

        var patron = locatePatronWith(holding);
        patron.remove(holding);

        if (holding.isLate()) {
            assessFine(holding, patron);
        }
        return holding.daysLate();
    }

    private void assessFine(Holding holding, Patron patron) {
        patron.addFine(holding.fine());
    }

    private Patron locatePatronWith(Holding holding) {
        for (var patron: new PatronService().allPatrons()) {
            for (var patHld : patron.holdingMap()) {
                if (holding.getBarcode().equals(patHld.getBarcode()))
                    return patron;
            }
        }
        return null;
    }
}