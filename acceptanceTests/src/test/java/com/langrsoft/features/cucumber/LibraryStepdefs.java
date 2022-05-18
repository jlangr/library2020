package com.langrsoft.features.cucumber;

import com.langrsoft.controller.HoldingResponse;
import com.langrsoft.controller.MaterialRequest;
import com.langrsoft.controller.PatronRequest;
import com.langrsoft.external.MaterialType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.langrsoft.features.library.MaterialRequestBuilder;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LibraryStepdefs {
    private final World world = new World();

//    @Inject
//    public LibraryStepdefs(World world) {
//        this.world = world;
//    }

    @Given("a clean library system")
    public void clear() {
        world.libraryClient.clear();
    }

    @Given("a library system with a branch named {string}")
    public void clearedSystemWithBranch(String name) {
        world.libraryClient.clear();
        world.libraryClient.addBranch(name);
    }

    @Given("a branch named {string} with the following holdings:")
    public void createBranchWithHoldings(String branchName, List<String> titles) {
        world.libraryClient.addBranch(branchName);
        world.libraryClient.useLocalClassificationService();
        world.libraryClient.addHoldingsWithTitles(titles, branchName);
    }

    @When("{word} add(s) a branch named {string}")
    public void addBranch(String user, String name) {
        System.out.println("user: " + user);
        world.libraryClient.addBranch(name);
    }

    @When("{word} requests a list of all branches")
    public void requestBranches(String user) {
        world.libraryClient.retrieveBranches(user);
    }

    @Then("^the system returns the following branches:?$")
    public void assertBranches(List<String> branchNames) {
        // TODO fix
//        var expected = branchNames.toArray(new String[branchNames.size()]);
//        assertThat(world.libraryClient.retrievedBranches())
//                .extracting(branchRequest -> branchRequest.getName())
//                .containsExactlyInAnyOrder(expected);
    }

    @Given("a patron checks out {string} on {oldSchoolDate}")
    public void patronChecksOutHolding(String title, Date checkoutDate) {
        world.libraryClient.addPatron("");
        world.checkoutResponse = world.libraryClient.checkOut(title, checkoutDate);
    }

    @Then("^\"(.*)\" (is|is not) available")
    public void assertAvailable(String title, String isOrIsNot) {
        assertThat(world.libraryClient.retrieveHoldingWithTitle(title).getIsAvailable(),
                equalTo(isOrIsNot.equals("is")));
    }

    @Then("the client is informed of a conflict")
    public void assertConflict() {
        assertThat(world.checkoutResponse, equalTo(409));
    }

    @Then("the due date for {string} is {oldSchoolDate}")
    public void assertDueDate(String title, Date dueDate) {
        assertThat(world.libraryClient.retrieveHoldingWithTitle(title).getDateDue(),
                equalTo(dueDate));
    }

    @When("{string} is returned on {oldSchoolDate} to {string}")
    public void bookReturnedOnTo(String title, Date checkinDate, String branchName) {
        world.libraryClient.checkIn(title, branchName, checkinDate);
    }

    @When("{string} is returned on {oldSchoolDate}")
    public void bookReturnedOn(String title, Date checkinDate) {
        world.libraryClient.checkIn(title, checkinDate);
    }

    @Then("the patron's fine balance is {int}")
    public void assertFineBalance(int expectedFineBalance) {
        assertThat(world.libraryClient.currentPatron().getFineBalance(), equalTo(expectedFineBalance));
    }

    @Given("a librarian adds a patron named {string}")
    public void addPatron(String name) {
        world.libraryClient.addPatron(name);
    }

    @When("a librarian requests a list of all patrons")
    public void requestPatrons() {
        world.libraryClient.retrievePatrons();
    }

    @Then("the client shows the following patrons:")
    public void assertPatrons(List<PatronRequest> expectedPatrons) {
        // TODO fix this
//        assertThat(world.libraryClient.retrievedPatrons())
//                .usingElementComparatorIgnoringFields("id")
//                .containsExactlyInAnyOrder(asArray(expectedPatrons, PatronRequest.class));
    }

    // see https://www.baeldung.com/cucumber-data-tables
    @Given("Xa local classification service with:")
    public void roteClassificationServiceData(List<List<String>> books) {
        world.libraryClient.useLocalClassificationService();
        world.libraryClient.addBooks(toMaterialRequestList(books));
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

    @Given("a local classification service with:")
    public void classificationServiceData(List<MaterialRequest> books) {
        world.libraryClient.useLocalClassificationService();
        world.libraryClient.addBooks(books);
    }

    @When("a librarian adds a book holding with source id {word} at branch {string}")
    public void addBookHolding(String sourceId, String branchName) {
        world.libraryClient.addHolding(sourceId, "???", branchName);
    }

    @Then("the {string} branch contains the following holdings:")
    public void assertBranchContains(String branchName, List<HoldingResponse> expectedHoldings) {
        // TODO fix
//        assertThat(world.libraryClient.retrieveHoldingsAtBranch(branchName))
//                .usingElementComparatorOnFields("barcode") // TODO add in as exercise
//                .containsExactlyInAnyOrder(asArray(expectedHoldings, HoldingResponse.class));
    }

    @Given("a request for the daily fine for a {materialType}")
    public void getDailyFine(MaterialType materialType) {
        world.libraryClient.getFineAmount(materialType);
    }

    @Then("the fine amount is {int}")
    public void assertDailyFineAmount(int expected) {
        assertThat(world.libraryClient.currentDailyFineAmount(), equalTo(expected));
    }
}