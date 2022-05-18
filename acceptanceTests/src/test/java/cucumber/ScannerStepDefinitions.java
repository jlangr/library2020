package cucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ScannerStepDefinitions {
    private World world = new World();

//    @Inject
    public ScannerStepDefinitions(World world) {
        this.world = world;
    }

    @When("the branch barcode for {string} is scanned")
    public void the_branch_barcode_for_is_scanned(String branchName) {
        String barcodeForBranch = barcodeForBranch(branchName);
        world.scannerClient.scanBarcode(barcodeForBranch);
        // fold into scanBranch?
    }

    private String barcodeForBranch(String branchName) {
        return world.libraryClient.branchScanCode(branchName);
    }

    @Then("the scanner identifies itself as belonging to the branch {string}")
    public void the_scanner_identifies_itself_as_belonging_to_the_branch(String branchName) {
        String actual = world.scannerClient.branchScanCode();
        assertThat(actual, equalTo(barcodeForBranch(branchName));
    }
}
