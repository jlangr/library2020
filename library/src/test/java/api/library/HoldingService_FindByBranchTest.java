package api.library;

import com.loc.material.api.ClassificationApi;
import com.loc.material.api.Material;
import domain.core.ClassificationApiFactory;
import domain.core.Holding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HoldingService_FindByBranchTest {
    private HoldingService service = new HoldingService();
    private ClassificationApi classificationApi = mock(ClassificationApi.class);
    private BranchService branchService = new BranchService();

    @BeforeEach
    public void initialize() {
        LibraryData.deleteAll();
        ClassificationApiFactory.setService(classificationApi);
    }

    private String addHolding(String sourceId, String branchScanCode) {
        when(classificationApi.retrieveMaterial(sourceId))
                .thenReturn(new Material(sourceId, "", "", "", ""));
        return service.add(sourceId, branchScanCode);
    }

    @Test
    public void returnsOnlyHoldingsAtBranch() {
        var branchAScanCode = branchService.add("branch A");
        var branchBScanCode = new BranchService().add("branch B");
        addHolding("123", branchAScanCode);
        addHolding("456", branchAScanCode);
        addHolding("999", branchBScanCode);

        var holdings = service.findByBranch(branchAScanCode);

        var holdingSourceIds = holdings.stream()
                .map(h -> h.getMaterial().getSourceId())
                .collect(Collectors.toList());
        assertThat(holdingSourceIds, equalTo(asList("123", "456")));
    }
}
