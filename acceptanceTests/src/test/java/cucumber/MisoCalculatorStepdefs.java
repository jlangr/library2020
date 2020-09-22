package cucumber;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MisoCalculatorStepdefs {
    @com.google.inject.Inject
    World world;

    @Given("the value {int} and {int}")
    public void inputTwoElements(Integer int1, Integer int2) {
        world.misoCalculator.setOperands(int1, int2);
    }

    @When("execute ADD operation")
    public void add() {
        world.misoCalculator.add();
    }

    @Then("the expected result is {int}")
    public void verifySum(int expected) {
        assertEquals(expected, world.misoCalculator.getResult());
    }
}
