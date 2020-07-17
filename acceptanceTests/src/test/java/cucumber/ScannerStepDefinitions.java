package cucumber;

import api.scanner.ScanStation;
import com.google.inject.Inject;
import com.nssi.devices.model1801c.ScanDisplayListener;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class ScannerStepDefinitions {
    private World world;

    @Inject
    public ScannerStepDefinitions(World world) {
        this.world = world;
    }

    @When("the branch barcode for {string} is scanned")
    public void the_branch_barcode_for_is_scanned(String branchName) {
        String barcodeForBranch = barcodeForBranch(branchName);
        System.out.println("barcode for branch = " + barcodeForBranch);
        world.scannerClient.scanBarcode(barcodeForBranch);
        // fold into scanBranch?
    }

    private String barcodeForBranch(String branchName) {
        return world.libraryClient.branchScanCode(branchName);
    }

    @Then("the scanner identifies itself as belonging to the branch {string}")
    public void the_scanner_identifies_itself_as_belonging_to_the_branch(String branchName) {
        System.out.println("expected barcode for branch = " + barcodeForBranch(branchName));
        String actual = world.scannerClient.branchScanCode();
        System.out.println("branch scan code is actually " + actual);
        assertThat(actual)
                .isEqualTo(barcodeForBranch(branchName));
    }
}
