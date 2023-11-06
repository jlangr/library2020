package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.Calculator;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorStepDefinitions {
    Calculator calculator;

    @Given("entry of the number {int}")
    public void entry_of_the_number(int n) {
        calculator.enter(n);

    }

    @When("add is pressed with a value {int}")
    public void add_is_pressed_with_a_value(int n) {
        calculator.add(n);
    }

    @Then("the calculator has the value {int}")
    public void the_calculator_has_the_value(int expected) {
        assertThat(calculator.value()).isEqualTo(expected);
    }
}

