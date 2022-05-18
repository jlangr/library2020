package com.langrsoft.service.library;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.langrsoft.util.ListUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static testutil.HasExactlyItems.hasExactlyItems;

class BranchServiceTest {
    private BranchService service;

    @BeforeEach
    void initialize() {
        service = new BranchService();
        LibraryData.deleteAll();
    }

    @Test
    void findsByScanCode() {
        service.add("name", "b2");

        var branch = service.find("b2");

        assertThat(branch.getName(), equalTo("name"));
    }

    @Test
    void rejectsDuplicateScanCode() {
        service.add("", "b559");
        assertThrows(DuplicateBranchCodeException.class, () ->
                service.add("", "b559"));
    }

    @Test
    void rejectsScanCodeNotStartingWithB() {
        assertThrows(InvalidBranchCodeException.class, () ->
                service.add("", "c2234"));
    }

    @Test
    void answersGeneratedId() {
        var scanCode = service.add("");

        assertThat(scanCode.startsWith("b"), equalTo(true));
    }

    @Test
    void findsBranchMatchingScanCode() {
        var scanCode = service.add("a branch");

        var branch = service.find(scanCode);

        assertThat(branch.getName(), equalTo("a branch"));
        assertThat(branch.getScanCode(), equalTo(scanCode));
    }

    @Test
    void returnsListOfAllPersistedBranches() {
        var eastScanCode = service.add("e");
        var westScanCode = service.add("w");

        var all = service.allBranches();

        var scanCodes = new ListUtil().map(all, "getScanCode", Branch.class, String.class);
        assertThat(scanCodes, hasExactlyItems(eastScanCode, westScanCode));
    }
}
