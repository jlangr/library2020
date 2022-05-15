package com.langrsoft.service.scanner;

import com.langrsoft.service.library.BranchService;
import com.langrsoft.service.library.HoldingService;
import com.langrsoft.service.library.PatronService;
import com.langrsoft.devices.nssi1801c.ScanDisplayListener;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

class MockedScannerSubsystemFields {
    protected ScanStation scanner;
    protected ScanStationState state;
    protected ScanDisplayListener display;

    protected HoldingService holdingService;
    protected PatronService patronService;
    protected BranchService branchService;

    @BeforeEach
    void createScanner() {
        display = mock(ScanDisplayListener.class);
        scanner = new ScanStation(display);

        holdingService = mock(HoldingService.class);
        scanner.setLibrarySystem(holdingService);

        patronService = mock(PatronService.class);
        scanner.setPatronService(patronService);

        branchService = mock(BranchService.class);
        scanner.setBranchService(branchService);
    }
}
