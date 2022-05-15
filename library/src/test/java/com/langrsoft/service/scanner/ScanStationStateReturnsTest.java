package com.langrsoft.service.scanner;

import com.langrsoft.domain.Branch;
import com.langrsoft.domain.Patron;
import org.junit.jupiter.api.Test;
import com.langrsoft.util.DateUtil;
import com.langrsoft.util.TimestampSource;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScanStationStateReturnsTest extends ScanStationStateTestBase {
    @Override
    protected ScanStationState createStateObject() {
        return new ScanStationStateReturns(scanner);
    }

    @Test
    void displaysWarningWhenCompletePressed() {
        state.pressComplete();

        assertMessageDisplayed(ScanStationStateReturns.MSG_WAITING_FOR_RETURNS);
        assertStateUnchanged();
    }

    @Test
    void changesStateToInventoryWhenInventoryCardPressed() {
        state.scanInventoryCard();

        assertCurrentState(ScanStationStateInventory.class);
        assertMessageDisplayed(ScanStationStateReturns.MSG_INVENTORY);
    }

    @Test
    void changesStateToCheckoutWhenPatronCardPressed() {
        var patron = new Patron("p222", "");
        when(patronService.find("p222")).thenReturn(patron);
        state.scanPatron("p222");

        assertCurrentState(ScanStationStateCheckout.class);
        assertThat(scanner.getPatronId(), equalTo("p222"));
    }

    @Test
    void changesBranchWhenBranchIdScanned() {
        var eastBranch = new Branch("b222", "");
        when(branchService.find("b222")).thenReturn(eastBranch);
        scanner.setBranch(new Branch("b9999", ""));

        state.scanBranchId("b222");

        assertStateUnchanged();
        assertThat(scanner.getBranchId(), equalTo(eastBranch.getScanCode()));
    }

    @Test
    void checksInBookWhenBarcodeScanned() {
        var branch = new Branch("b123", "East");
        scanner.setBranch(branch);
        var checkinDate = DateUtil.create(2017, Calendar.MARCH, 17);
        TimestampSource.queueNextTime(checkinDate);

        state.scanHolding("123:1");

        verify(holdingService).checkIn("123:1", checkinDate, branch.getScanCode());
        assertMessageDisplayed(String.format(ScanStationStateReturns.MSG_CHECKED_IN, "123:1"));
        assertStateUnchanged();
    }
}
