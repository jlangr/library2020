package api.library;

import domain.core.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ListUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.matchers.HasExactlyItems.hasExactlyItems;

public class BranchServiceTest {
    private BranchService service;

    @BeforeEach
    public void initialize() {
        service = new BranchService();
        LibraryData.deleteAll();
    }

    @Test
    public void findsByScanCode() {
        service.add("name", "b2");

        var branch = service.find("b2");

        assertThat(branch.getName(), equalTo("name"));
    }

    @Test
    public void rejectsDuplicateScanCode() {
        service.add("", "b559");
        assertThrows(DuplicateBranchCodeException.class, () ->
                service.add("", "b559"));
    }

    @Test
    public void rejectsScanCodeNotStartingWithB() {
        assertThrows(InvalidBranchCodeException.class, () ->
                service.add("", "c2234"));
    }

    @Test
    public void answersGeneratedId() {
        String scanCode = service.add("");

        assertThat(scanCode.startsWith("b"), equalTo(true));
    }

    @Test
    public void findsBranchMatchingScanCode() {
        var scanCode = service.add("a branch");

        var branch = service.find(scanCode);

        assertThat(branch.getName(), equalTo("a branch"));
        assertThat(branch.getScanCode(), equalTo(scanCode));
    }

    @Test
    public void returnsListOfAllPersistedBranches() {
        var eastScanCode = service.add("e");
        var westScanCode = service.add("w");

        var all = service.allBranches();

        var scanCodes = new ListUtil().map(all, "getScanCode", Branch.class, String.class);
        assertThat(scanCodes, hasExactlyItems(eastScanCode, westScanCode));
    }
}
