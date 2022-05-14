package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.BranchNotFoundException;
import domain.core.ClassificationApiFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingServiceTest {
    HoldingService service = new HoldingService();
    ClassificationApi classificationApi = mock(ClassificationApi.class);
    String branchScanCode;

    @BeforeEach
    void initialize() {
        LibraryData.deleteAll();
        ClassificationApiFactory.setService(classificationApi);
        branchScanCode = new BranchService().add("");
    }

    @Test
    void usesClassificationServiceToRetrieveBookDetails() {
        var isbn = "9780141439594";
        var material = new Material(isbn, "", "", "", "");
        when(classificationApi.retrieveMaterial(isbn)).thenReturn(material);
        var barcode = service.add(isbn, branchScanCode);

        var holding = service.find(barcode);

        assertThat(holding.getMaterial().getSourceId(), equalTo(isbn));
    }

    @Test
    void throwsExceptionWhenBranchNotFound() {
        var thrown = assertThrows(BranchNotFoundException.class, () ->
                    service.add("", "badBranchId"));
        assertThat(thrown.getMessage(), equalTo("Branch not found: badBranchId"));
    }
}
