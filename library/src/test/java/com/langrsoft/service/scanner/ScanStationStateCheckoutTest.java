package com.langrsoft.service.scanner;

import com.langrsoft.domain.Holding;
import com.langrsoft.domain.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.langrsoft.util.TimestampSource;

import java.util.Date;

import static com.langrsoft.service.scanner.ScanStationStateCheckout.*;
import static java.util.Calendar.JANUARY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static com.langrsoft.util.DateUtil.create;

class ScanStationStateCheckoutTest extends ScanStationStateTestBase {
    static final String PATRON_JOE_ID = "p111";
    static final Patron PATRON_JOE = new Patron(PATRON_JOE_ID, "Joe");
    static final Date NEW_YEARS_DAY = create(2011, JANUARY, 1);

    Holding holdingWithAvailability;
    Holding holdingWithUnavailability;

    @Override
    protected ScanStationState createStateObject() {
        return new ScanStationStateCheckout(scanner);
    }

    @BeforeEach
    void initialize() {
        holdingWithAvailability = createHoldingWithAvailability(true);
        holdingWithUnavailability = createHoldingWithAvailability(false);
    }

    Holding createHoldingWithAvailability(boolean isAvailable) {
        Holding holding = mock(Holding.class);
        when(holding.isAvailable()).thenReturn(isAvailable);
        return holding;
    }

    @BeforeEach
    void addPatronJoe() {
        when(patronService.find(PATRON_JOE_ID)).thenReturn(PATRON_JOE);
    }

    @Test
    void displaysWarningWhenPatronCardScanned() {
        state.scanPatron(PATRON_JOE_ID);

        assertMessageDisplayed(MSG_COMPLETE_CHECKOUT_FIRST);
        assertStateUnchanged();
    }

    @Test
    void displaysWarningWhenInventoryCardScanned() {
        state.scanInventoryCard();

        assertMessageDisplayed(MSG_COMPLETE_CHECKOUT_FIRST);
        assertStateUnchanged();
    }

    @Test
    void displaysMessageIfNoHoldingExists() {
        scanner.scanPatronId(PATRON_JOE_ID);
        when(holdingService.find("123:1")).thenReturn(null);

        state.scanHolding("123:1");

        assertMessageDisplayed(String.format(MSG_INVALID_HOLDING_ID, "123:1"));
        assertStateUnchanged();
    }

    @Test
    void checksOutHoldingWhenHoldingIdScanned() {
        scanner.scanPatronId(PATRON_JOE_ID);
        when(holdingService.find("123:1")).thenReturn(holdingWithAvailability);
        TimestampSource.queueNextTime(NEW_YEARS_DAY);

        state.scanHolding("123:1");

        verify(holdingService).checkOut(PATRON_JOE_ID, "123:1", NEW_YEARS_DAY);
        assertMessageDisplayed(String.format(MSG_CHECKED_OUT, "123:1"));
        assertStateUnchanged();
    }

    @Test
    void displaysMessageWhenBookCheckedOutTwice() {
        scanner.scanPatronId(PATRON_JOE_ID);
        when(holdingService.find("123:1")).thenReturn(holdingWithAvailability);
        state.scanHolding("123:1");
        when(holdingService.find("123:1")).thenReturn(holdingWithUnavailability);

        state.scanHolding("123:1");

        assertMessageDisplayed(String.format(ScanStationStateCheckout.MSG_ALREADY_CHECKED_OUT, "123:1"));
        assertStateUnchanged();
        assertThat(TimestampSource.isExhausted(), equalTo(true));
    }

    @Test
    void changesStateToReturnsWhenCompletePressed() {
        scanner.scanPatronId(PATRON_JOE_ID);

        state.pressComplete();

        assertCurrentState(ScanStationStateReturns.class);
        assertMessageDisplayed(String.format(MSG_COMPLETED_CHECKOUT, PATRON_JOE_ID));
    }

    @Test
    void displaysWarningWhenBranchIdScanned() {
        state.scanBranchId("b123");

        assertStateUnchanged();
        assertMessageDisplayed(MSG_COMPLETE_CHECKOUT_FIRST);
    }
}
