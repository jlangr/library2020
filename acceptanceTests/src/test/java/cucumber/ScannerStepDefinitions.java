package cucumber;

import api.scanner.ScanStation;
import com.nssi.devices.model1801c.ScanDisplayListener;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import library.ScannerClient;

public class ScannerStepDefinitions {
    ScannerClient scanner = new ScannerClient();

    @When("the branch barcode for {string} is scanned")
    public void the_branch_barcode_for_is_scanned(String branchName) {
        scanner.scanBarcode(scanner.barcodeForBranch(branchName));
    }

    @Then("the scanner identifies itself as belonging to the branch {string}")
    public void the_scanner_identifies_itself_as_belonging_to_the_branch(String branchName) {
    }
}
