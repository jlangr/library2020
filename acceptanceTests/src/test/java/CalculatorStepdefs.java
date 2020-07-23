import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.Calculator;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorStepdefs {
    Calculator calculator = new Calculator();
    @Given("entry of the number {int}")
    public void entryOfTheNumber(int number) {
        calculator.enter(number);
    }

    @When("add is pressed with a value {int}")
    public void addIsPressedWithAValue(int number) {
        calculator.add(number);
    }

    @Then("the calculator has the value {int}")
    public void theCalculatorHasTheValue(int number) {
        assertThat(calculator.value()).isEqualTo(number);
    }

    @When("square is pressed")
    public void squareIsPressed() {
        calculator.square();
    }
}
