package com.langrsoft.service.scanner;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.Test;

import static com.langrsoft.service.scanner.ScanStationStateInventory.MSG_COMPLETE_INVENTORY_FIRST;
import static com.langrsoft.service.scanner.ScanStationStateInventory.MSG_SCANNED_HOLDING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScanStationStateInventoryTest extends ScanStationStateTestBase {
    @Override
    protected ScanStationStateInventory createStateObject() {
        return new ScanStationStateInventory(scanner);
    }

    @Test
    void displaysWarningWhenInventoryCardScanned() {
        state.scanInventoryCard();

        assertStateRetainedWithMessage(MSG_COMPLETE_INVENTORY_FIRST);
    }

    private void assertStateRetainedWithMessage(String message) {
        assertMessageDisplayed(message);
        assertStateUnchanged();
    }

    @Test
    void changesBranchWhenBranchIdScanned() {
        when(branchService.find("b222")).thenReturn(new Branch("b222", "West"));

        state.scanBranchId("b222");

        assertThat(scanner.getBranchId(), equalTo("b222"));
        assertMessageDisplayed(String.format(ScanStation.MSG_BRANCH_SET_TO, "West"));
        assertStateUnchanged();
    }

    @Test
    void addsNewHoldingToLibraryWhenSourceIdScanned() {
        var sourceId = "1234567890123";
        scanner.setBranch(new Branch("b123", ""));

        state.scanHolding(sourceId);

        verify(holdingService).add(sourceId, "b123");
        assertStateUnchanged();
        assertMessageDisplayed(String.format(MSG_SCANNED_HOLDING, sourceId));
    }

    @Test
    void displaysWarningWhenPatronCardScanned() {
        state.scanPatron("p123");

        assertStateRetainedWithMessage(MSG_COMPLETE_INVENTORY_FIRST);
    }

    @Test
    void changesStateToReturnsWhenCompletePressed() {
        state.pressComplete();

        assertThat(scanner.getCurrentState(), is(instanceOf(ScanStationStateReturns.class)));
    }
}
