package com.langrsoft.service.scanner;

import com.langrsoft.service.library.BranchService;
import com.langrsoft.service.library.HoldingService;
import com.langrsoft.service.library.PatronService;
import com.langrsoft.devices.nssi1801c.ScanDisplayListener;
import com.langrsoft.devices.nssi1801c.ScanListener;
import com.langrsoft.domain.Branch;
import com.langrsoft.domain.Patron;

public class ScanStation implements ScanListener {
    public static final String MSG_NONEXISTENT_BRANCH = "Nonexistent branch id %s";
    public static final String MSG_NONEXISTENT_PATRON = "Nonexistent patron id %s";
    public static final String MSG_BRANCH_SET_TO = "Branch now set to %s";
    public static final String MSG_PATRON_SET_TO = "Patron now set to %s";
    public static final String MSG_BAR_CODE_NOT_RECOGNIZED = "Bar code not recognized.";

    private HoldingService librarySystem = new HoldingService();
    private PatronService patronService = new PatronService();
    private BranchService branchService = new BranchService();
    private ScanStationState state = new ScanStationStateWaiting(this);
    private final ScanDisplayListener display;
    private Branch branch = Branch.CHECKED_OUT;
    private Patron patron;

    public ScanStation(ScanDisplayListener display) {
        this.display = display;
    }

    @Override
    public void scan(String barcode) {
        switch (BarcodeInterpreter.typeOf(barcode)) {
            case BRANCH:
                state.scanBranchId(barcode);
                break;
            case INVENTORY:
                state.scanInventoryCard();
                break;
            case HOLDING:
                state.scanHolding(barcode);
                break;
            case PATRON:
                state.scanPatron(barcode);
                break;
            case UNRECOGNIZED:
                showMessage(MSG_BAR_CODE_NOT_RECOGNIZED);
                break;
        }
    }

    @Override
    public void pressComplete() {
        state.pressComplete();
    }

    public ScanStationState getCurrentState() {
        return state;
    }

    public void setCurrentState(ScanStationState state) {
        this.state = state;
    }

    public void setLibrarySystem(HoldingService librarySystem) {
        this.librarySystem = librarySystem;
    }

    public HoldingService getLibrarySystem() {
        return librarySystem;
    }

    public void setPatronService(PatronService patronService) {
        this.patronService = patronService;
    }

    public void setBranchService(BranchService branchService) {
        this.branchService = branchService;
    }

    public void showMessage(String text) {
        display.showMessage(text);
    }

    public void scanPatronId(String patronId) {
        var retrievedPatron = patronService.find(patronId);
        if (retrievedPatron == null) {
            showMessage(String.format(ScanStation.MSG_NONEXISTENT_PATRON, patronId));
            return;
        }
        this.patron = retrievedPatron;
        showMessage(String.format(ScanStation.MSG_PATRON_SET_TO, patron.getName()));
    }

    public String getPatronId() {
        return patron == null ? null : patron.getId();
    }

    public Patron getPatron() {
        return patron;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getBranchId() {
        return branch.getScanCode();
    }

    public void scanBranchId(String branchId) {
        var foundBranch = branchService.find(branchId);
        if (foundBranch == null) {
            showMessage(String.format(ScanStation.MSG_NONEXISTENT_BRANCH, branchId));
            return;
        }
        this.branch = foundBranch;
        showMessage(String.format(ScanStation.MSG_BRANCH_SET_TO, foundBranch.getName()));
    }

    public Branch getBranch() {
        return branch;
    }
}
