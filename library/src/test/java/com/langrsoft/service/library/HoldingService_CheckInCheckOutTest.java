package com.langrsoft.service.library;

import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.Material;
import com.langrsoft.external.MaterialType;
import com.langrsoft.domain.ClassificationApiFactory;
import com.langrsoft.domain.HoldingAlreadyCheckedOutException;
import com.langrsoft.domain.HoldingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.langrsoft.util.DateUtil;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static testutil.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

class HoldingService_CheckInCheckOutTest {
    HoldingService service = new HoldingService();
    PatronService patronService = new PatronService();
    ClassificationApi classificationApi = mock(ClassificationApi.class);
    String patronId;
    String branchScanCode;
    String bookHoldingBarcode;

    @BeforeEach
    void initialize() {
        LibraryData.deleteAll();
        ClassificationApiFactory.setService(classificationApi);
        branchScanCode = new BranchService().add("a branch name");
        patronId = patronService.add("joe");
        bookHoldingBarcode = addBookHolding();
    }

    private String addBookHolding() {
        var material = new Material("123", "", "", "", MaterialType.BOOK, "");
        when(classificationApi.retrieveMaterial("123")).thenReturn(material);
        return service.add("123", branchScanCode);
    }

    @Test
    void holdingMadeUnavailableOnCheckout() {
        service.checkOut(patronId, bookHoldingBarcode, new Date());

        assertThat(service.isAvailable(bookHoldingBarcode), equalTo(false));
    }

    @Test
    void checkoutThrowsWhenHoldingIdNotFound() {
        var date = new Date();
        assertThrows(HoldingNotFoundException.class, () ->
                service.checkOut(patronId, "999:1", date));
    }

    @Test
    void checkoutThrowsWhenUnavailable() {
        var date = new Date();
        service.checkOut(patronId, bookHoldingBarcode, date);

        assertThrows(HoldingAlreadyCheckedOutException.class, () ->
                service.checkOut(patronId, bookHoldingBarcode, date));
    }

    @Test
    void updatesPatronWithHoldingOnCheckout() {
        service.checkOut(patronId, bookHoldingBarcode, new Date());

        var patronHoldings = patronService.find(patronId).holdingMap();
        assertThat(patronHoldings.holdings(), hasExactlyItemsInAnyOrder(service.find(bookHoldingBarcode)));
    }

    @Test
    void returnsHoldingToBranchOnCheckIn() {
        service.checkOut(patronId, bookHoldingBarcode, new Date());

        service.checkIn(bookHoldingBarcode, DateUtil.tomorrow(), branchScanCode);

        var holding = service.find(bookHoldingBarcode);
        assertThat(holding.isAvailable(), equalTo(true));
        assertThat(holding.getBranch().getScanCode(), equalTo(branchScanCode));
    }

    @Test
    void removesHoldingFromPatronOnCheckIn() {
        service.checkOut(patronId, bookHoldingBarcode, new Date());

        service.checkIn(bookHoldingBarcode, DateUtil.tomorrow(), branchScanCode);

        assertThat(patronService.find(patronId).holdingMap().isEmpty(), is(true));
    }

    @Nested
    class DateDue {
        @Test
        void returnsDateDueFromService() {
            service.checkOut(patronId, bookHoldingBarcode, new Date());

            var due = service.dateDue(bookHoldingBarcode);

            var holding = service.find(bookHoldingBarcode);
            assertThat(due, equalTo(holding.dateDue()));
        }

        @Test
        void throwsWhenHoldingNotFound() {
            assertThrows(HoldingNotFoundException.class, () ->
                    service.dateDue("NONEXISTENT:1"));
        }
    }

    @Test
    void checkinReturnsDaysLate() {
        service.checkOut(patronId, bookHoldingBarcode, new Date());
        var fiveDaysLate = DateUtil.addDays(service.dateDue(bookHoldingBarcode), 5);

        var daysLate = service.checkIn(bookHoldingBarcode, fiveDaysLate, branchScanCode);

        assertThat(daysLate, equalTo(5));
    }

    @Test
    void libraryInexplicablyIgnoresLeapDayWhenConsideringLateDays() {
        var dateDue = DateUtil.create(2024, 1, 29);
        var checkoutDate = DateUtil.addDays(dateDue, -1 * MaterialType.BOOK.getCheckoutPeriod());
        service.checkOut(patronId, bookHoldingBarcode, checkoutDate);
        var holding = service.find(bookHoldingBarcode);
        var oneDayLate = DateUtil.addDays(holding.dateDue(), 1);

        var daysLate = service.checkIn(bookHoldingBarcode, oneDayLate, branchScanCode);

        assertThat(daysLate, equalTo(0));
    }

    @Test
    void updatesFinesOnLateCheckIn() {
        service.checkOut(patronId, bookHoldingBarcode, new Date());
        var holding = service.find(bookHoldingBarcode);
        var oneDayLate = DateUtil.addDays(holding.dateDue(), 1);

        service.checkIn(bookHoldingBarcode, oneDayLate, branchScanCode);

        assertThat(patronService.find(patronId).fineBalance(), equalTo(MaterialType.BOOK.getDailyFine()));
    }
}
