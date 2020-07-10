package cucumber;

import controller.MaterialRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import library.LibraryClient;
import util.Calculator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

// TODO [x] use PicoContainer and injection between stepdefs?
public class Stepdefs {
    public static final String YMD = "yyyy/MM/dd";
    private LibraryClient libraryClient = new LibraryClient();
    private int checkoutResponse;

    // TODO is this really necessary or does this already exist
    @ParameterType("[A-Z]+(,\\s*[A-Za-z])*")
    public List<String> wordList(String... commaSeparatedText) {
        return asList(commaSeparatedText);
    }

    @ParameterType("\\d+/\\d+/\\d+")
    public LocalDate date(String date) {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(date, formatter);
    }

    @ParameterType("\\d+/\\d+/\\d+")
    public java.util.Date oldSchoolDate(String dateString) {
        return Date.from(date(dateString).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // ===

    @Given("a clean library system")
    public void clear() {
        libraryClient.clear();
    }

    @Given("a library system with a branch named {string}")
    public void clearedSystemWithBranch(String name) {
        libraryClient.clear();
        libraryClient.addBranch(name);
    }

    //   @Given("a branch named {string} with the following holdings: (.*)$")
//   public void createBranchWithHoldings(String branchName, List<String> titles) {
    @Given("a branch named {string} with the following holdings:")
    public void createBranchWithHoldings(String branchName, List<String> titles) {
        libraryClient.addBranch(branchName);
        libraryClient.useLocalClassificationService();
        libraryClient.addHoldingsWithTitles(titles, branchName);
    }

    // TODO hmm how else can this be done
//   @When("{string} adds? a branch named \"(.*)\"")
    @When("{word} add a branch named {word}")
    @When("{word} adds a branch named {word}")
    public void addBranch(String user, String name) {
        libraryClient.addBranch(name);
    }

    // @Given("a (?:color|colour) named (.*)")
    // TODO hmm
    @Given("a color named {word}")
    public void x(String color) {
        System.out.println("color => " + color);
    }

    @When("{word} requests a list of all branches")
    public void requestBranches(String user) {
        libraryClient.retrieveBranches(user);
    }

    // TODO optional colon @Then("the system returns the following branches:?")
    @Then("the system returns the following branches:")
    public void assertBranches(DataTable expectedBranches) {
// TODO      expectedBranches.unorderedDiff(libraryClient.retrievedBranches());
    }

    // TODO cannot mix and match cucumber expressions & regex
    // TODO but what about anchors ^ and $
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
    public void assertPatrons(DataTable expectedPatrons) {
        // TODO
//      expectedPatrons.unorderedDiff(libraryClient.retrievedPatrons());
    }

    // see https://www.baeldung.com/cucumber-data-tables
    @Given("Xa local classification service with:")
    public void roteClassificationServiceData(List<List<String>> books) {
        libraryClient.useLocalClassificationService();
        List<MaterialRequest> bookRequests =
                books.stream()
                        .skip(1)
                        .map(book -> {
                            MaterialRequest request = new MaterialRequest();
                            request.setSourceId(book.get(0));
                            request.setClassification(book.get(1));
                            request.setFormat(book.get(2));
                            return request;
                        })
                        .collect(toList());
        libraryClient.addBooks(bookRequests);
    }

    @DataTableType
    public MaterialRequest createMaterialRequest(Map<String, String> tableEntry) {
        MaterialRequest request = new MaterialRequest();
        request.setSourceId(tableEntry.get("source id")); // note the space!
        request.setClassification(tableEntry.get("classification"));
        request.setFormat(tableEntry.get("format"));
        System.out.println("created request " + request);
        return request;
    }

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
    public void assertBranchContains(String branchName, DataTable holdings) {
// TODO       holdings.unorderedDiff(libraryClient.retrieveHoldingsAtBranch(branchName));
    }

    // ===

    Calculator calculator;

    @Given("entry of the number {int}")
    public void entry_of_the_number(int number) {
        calculator = new Calculator();
        calculator.enter(number);
    }

    // TODO both should work
//   @When("^add is pressed with a value (\\d+)$")
//   public void add_is_pressed_with_a_value(int number) {
//      calculator.add(number);
//   }

    @When("add is pressed with a value {int}")
    public void add_pressed_with(int number) {
        calculator.add(number);
    }

    @When("square is pressed")
    public void square_is_pressed() {
        calculator.square();
    }

    @Then("the calculator has the value {int}")
    public void the_calculator_has_the_value(int expectedNumber) {
        assertThat(calculator.value()).isEqualTo(expectedNumber);
    }
}