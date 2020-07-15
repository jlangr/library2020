package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.Calculator;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorStepDefinitions {
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

