package com.langrsoft.features.library;

import com.langrsoft.service.scanner.ScanStation;
import com.langrsoft.devices.nssi1801c.ScanDisplayListener;

public class ScannerClient implements ScanDisplayListener {

    private ScanDisplayListener display = this;
    public ScanStation scanner = new ScanStation(display);
    private LibraryClient libraryClient;

    public ScannerClient(LibraryClient libraryClient) {
        this.libraryClient = libraryClient;
    }

    @Override
    public void showMessage(String text) {

    }

    public void scanBarcode(String barcodeForBranch) {
        scanner.scan(barcodeForBranch);
    }

    public String branchScanCode() {
        return scanner.getBranchId();
    }
}
