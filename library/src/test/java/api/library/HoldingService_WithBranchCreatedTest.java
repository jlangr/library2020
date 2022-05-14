package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.ClassificationApiFactory;
import domain.core.HoldingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HoldingService_WithBranchCreatedTest {
    private HoldingService service = new HoldingService();
    private ClassificationApi classificationApi = mock(ClassificationApi.class);
    private String branchScanCode;

    @BeforeEach
    public void initialize() {
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
    public void returnsEntireInventoryOfHoldings() {
        for (int i = 0; i < 3; i++)
            addHolding();

        var holdings = service.allHoldings();

        assertThat(holdings.size(), equalTo(3));
    }

    @Test
    public void storesNewHoldingAtBranch() {
        var barcode = addHolding();

        assertThat(service.find(barcode).getBranch().getScanCode(), equalTo(branchScanCode));
    }

    @Test
    public void findByBarCodeReturnsNullWhenNotFound() {
        assertThat(service.find("999:1"), nullValue());
    }

    @Test
    public void updatesBranchOnHoldingTransfer() {
        var barcode = addHolding();

        service.transfer(barcode, branchScanCode);

        var holding = service.find(barcode);
        assertThat(holding.getBranch().getScanCode(), equalTo(branchScanCode));
    }

    @Test
    public void throwsOnTransferOfNonexistentHolding() {
        assertThrows(HoldingNotFoundException.class, () ->
                service.transfer("XXX:1", branchScanCode));
    }

    @Test
    public void holdingIsAvailableWhenNotCheckedOut() {
        var barcode = addHolding();

        assertThat(service.isAvailable(barcode), equalTo(true));
    }

    @Test
    public void availabilityCheckThrowsWhenHoldingNotFound() {
        assertThrows(HoldingNotFoundException.class, () ->
                service.isAvailable("345:1"));
    }

    @Test
    public void checkinThrowsWhenHoldingIdNotFound() {
        assertThrows(HoldingNotFoundException.class, () ->
                service.checkIn("999:1", new Date(), branchScanCode));
    }
}
