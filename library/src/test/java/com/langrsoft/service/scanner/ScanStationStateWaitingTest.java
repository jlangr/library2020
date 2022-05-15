package com.langrsoft.service.scanner;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.Test;

import static com.langrsoft.service.scanner.ScanStationStateWaiting.MSG_SCAN_BRANCH_ID_FIRST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

class ScanStationStateWaitingTest extends ScanStationStateTestBase {
    @Override
    protected ScanStationState createStateObject() {
        return new ScanStationStateWaiting(scanner);
    }

    @Test
    void storesBranchIdWhenBranchCardScanned() {
        var westBranch = new Branch("b123", "west");
        when(branchService.find("b123")).thenReturn(westBranch);
        scanner.setBranch(new Branch("b999", "other"));

        state.scanBranchId("b123");

        assertCurrentState(ScanStationStateReturns.class);
        assertMessageDisplayed(String.format(ScanStation.MSG_BRANCH_SET_TO, westBranch.getName()));
        assertThat(scanner.getBranchId(), equalTo("b123"));
    }

    @Test
    void displaysWarningMessageOnWhenHoldingScanned() {
        state.scanHolding("");

        assertStateUnchanged();
        assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
    }

    @Test
    void displaysWarningMessageOnWhenInventoryCardScanned() {
        state.scanInventoryCard();

        assertStateUnchanged();
        assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
    }

    @Test
    void displaysWarningMessageOnWhenPatronScanned() {
        state.scanPatron("");

        assertStateUnchanged();
        assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
    }

    @Test
    void displaysWarningMessageOnWhenCompletePressed() {
        state.pressComplete();

        assertStateUnchanged();
        assertMessageDisplayed(MSG_SCAN_BRANCH_ID_FIRST);
    }
}
