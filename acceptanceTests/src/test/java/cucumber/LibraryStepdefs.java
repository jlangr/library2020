package cucumber;

import com.loc.material.api.MaterialType;
import controller.BranchRequest;
import controller.HoldingResponse;
import controller.MaterialRequest;
import controller.PatronRequest;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import library.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.ArrayUtils.asArray;

// TODO [x] use PicoContainer and injection between stepdefs?
public class LibraryStepdefs {
    private LibraryClient libraryClient = new LibraryClient();
    private int checkoutResponse;

    @Given("a clean library system")
    public void clear() {
        libraryClient.clear();
    }

    @Given("a library system with a branch named {string}")
    public void clearedSystemWithBranch(String name) {
        libraryClient.clear();
        libraryClient.addBranch(name);
    }

    @Given("a branch named {string} with the following holdings:")
    public void createBranchWithHoldings(String branchName, List<String> titles) {
        libraryClient.addBranch(branchName);
        libraryClient.useLocalClassificationService();
        libraryClient.addHoldingsWithTitles(titles, branchName);
    }

    @When("{word} add(s) a branch named {string}")
    public void addBranch3(String user, String name) {
        libraryClient.addBranch(name);
    }

    @When("{word} requests a list of all branches")
    public void requestBranches(String user) {
        libraryClient.retrieveBranches(user);
    }

    @Then("^the system returns the following branches:?$")
    public void assertBranches(List<String> branchNames) {
        var expected = branchNames.toArray(new String[branchNames.size()]);
        assertThat(libraryClient.retrievedBranches())
                .extracting(branchRequest -> branchRequest.getName())
                .containsExactlyInAnyOrder(expected);
    }

    @Given("a patron checks out {string} on {oldSchoolDate}")
    public void patronChecksOutHolding(String title, Date checkoutDate) {
        libraryClient.addPatron("");
        checkoutResponse = libraryClient.checkOut(title, checkoutDate);
    }

    @Then("^\"(.*)\" (is|is not) available")
    public void assertAvailable(String title, String isOrIsNot) {
        assertThat(libraryClient.retrieveHoldingWithTitle(title).getIsAvailable())
                .isEqualTo(isOrIsNot.equals("is"));
    }

    @Then("the client is informed of a conflict")
    public void assertConflict() {
        assertThat(checkoutResponse).isEqualTo(409);
    }

    @Then("the due date for {string} is {oldSchoolDate}")
    public void assertDueDate(String title, Date dueDate) {
        assertThat(libraryClient.retrieveHoldingWithTitle(title).getDateDue())
                .isEqualTo(dueDate);
    }

    @Then("the time your reservation ends is {time}")
    public void the_time_your_reservation_ends_is(LocalTime time) {
        // ?
    }

    @When("{string} is returned on {oldSchoolDate} to {string}")
    public void bookReturnedOnTo(String title, Date checkinDate, String branchName) {
        libraryClient.checkIn(title, branchName, checkinDate);
    }

    @When("{string} is returned on {oldSchoolDate}")
    public void bookReturnedOn(String title, Date checkinDate) {
        libraryClient.checkIn(title, checkinDate);
    }

    @Then("the patron's fine balance is {int}")
    public void assertFineBalance(int expectedFineBalance) {
        assertThat(libraryClient.currentPatron().getFineBalance()).isEqualTo(expectedFineBalance);
    }

    @Given("a librarian adds a patron named {string}")
    public void addPatron(String name) {
        libraryClient.addPatron(name);
    }

    @When("a librarian requests a list of all patrons")
    public void requestPatrons() {
        libraryClient.retrievePatrons();
    }

    @Then("the client shows the following patrons:")
    public void assertPatrons(List<PatronRequest> expectedPatrons) {
        assertThat(libraryClient.retrievedPatrons())
                .usingElementComparatorIgnoringFields("id") // TODO add in as exercise
                .containsExactlyInAnyOrder(asArray(expectedPatrons, PatronRequest.class));
    }

    // see https://www.baeldung.com/cucumber-data-tables
    @Given("Xa local classification service with:")
    public void roteClassificationServiceData(List<List<String>> books) {
        libraryClient.useLocalClassificationService();
        libraryClient.addBooks(toMaterialRequestList(books));
    }

    private List<MaterialRequest> toMaterialRequestList(List<List<String>> books) {
        return books.stream()
                .skip(1) // the header
                .map(book -> new MaterialRequestBuilder()
                        .sourceId(book.get(0))
                        .classification(book.get(1))
                        .format(book.get(2)).build())
                .collect(toList());
    }

    //    @Given("a local classification service with:")
//    public void classificationServiceData(List<MaterialRequest> books) {
//        libraryClient.useLocalClassificationService();
//        libraryClient.addBooks(books);
//    }
    @Given("a local classification service with:")
    public void classificationServiceData(List<MaterialRequest> books) {
        libraryClient.useLocalClassificationService();
        libraryClient.addBooks(books);
    }

    @When("a librarian adds a book holding with source id {word} at branch {string}")
    public void addBookHolding(String sourceId, String branchName) {
        libraryClient.addHolding(sourceId, "???", branchName);
    }

    @Then("the {string} branch contains the following holdings:")
    public void assertBranchContains(String branchName, List<HoldingResponse> expectedHoldings) {
        assertThat(libraryClient.retrieveHoldingsAtBranch(branchName))
                .usingElementComparatorOnFields("barcode") // TODO add in as exercise
                .containsExactlyInAnyOrder(asArray(expectedHoldings, HoldingResponse.class));
    }

    @Given("a request for the daily fine for a {materialType}")
    public void getDailyFine(MaterialType materialType) {
        libraryClient.getFineAmount(materialType);
    }

    @Then("the fine amount is {int}")
    public void assertDailyFineAmount(int expected) {
        assertThat(libraryClient.currentDailyFineAmount()).isEqualTo(expected);
    }
}