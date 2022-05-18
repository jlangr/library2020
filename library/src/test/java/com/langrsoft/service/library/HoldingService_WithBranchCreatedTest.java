package com.langrsoft.service.library;

import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.Material;
import com.langrsoft.domain.ClassificationApiFactory;
import com.langrsoft.domain.HoldingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingService_WithBranchCreatedTest {
    HoldingService service = new HoldingService();
    ClassificationApi classificationApi = mock(ClassificationApi.class);
    String branchScanCode;

    @BeforeEach
    void initialize() {
        LibraryData.deleteAll();
        ClassificationApiFactory.setService(classificationApi);
        branchScanCode = new BranchService().add("a branch name");
    }

    private String addHolding() {
        var material = new Material("123", "", "", "", "");
        when(classificationApi.retrieveMaterial("123")).thenReturn(material);
        return service.add("123", branchScanCode);
    }

    @Test
    void returnsEntireInventoryOfHoldings() {
        for (var i = 0; i < 3; i++)
            addHolding();

        var holdings = service.allHoldings();

        assertThat(holdings.size(), equalTo(3));
    }

    @Test
    void storesNewHoldingAtBranch() {
        var barcode = addHolding();

        assertThat(service.find(barcode).getBranch().getScanCode(), equalTo(branchScanCode));
    }

    @Test
    void findByBarCodeReturnsNullWhenNotFound() {
        assertThat(service.find("999:1"), nullValue());
    }

    @Test
    void updatesBranchOnHoldingTransfer() {
        var barcode = addHolding();

        service.transfer(barcode, branchScanCode);

        var holding = service.find(barcode);
        assertThat(holding.getBranch().getScanCode(), equalTo(branchScanCode));
    }

    @Test
    void throwsOnTransferOfNonexistentHolding() {
        assertThrows(HoldingNotFoundException.class, () ->
                service.transfer("XXX:1", branchScanCode));
    }

    @Test
    void holdingIsAvailableWhenNotCheckedOut() {
        var barcode = addHolding();

        assertThat(service.isAvailable(barcode), equalTo(true));
    }

    @Test
    void availabilityCheckThrowsWhenHoldingNotFound() {
        assertThrows(HoldingNotFoundException.class, () ->
                service.isAvailable("345:1"));
    }

    @Test
    void checkinThrowsWhenHoldingIdNotFound() {
        var date = new Date();
        assertThrows(HoldingNotFoundException.class, () ->
                service.checkIn("999:1", date, branchScanCode));
    }
}
