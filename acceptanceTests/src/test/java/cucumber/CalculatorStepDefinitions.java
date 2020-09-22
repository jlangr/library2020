package cucumber;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.Calculator;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorStepDefinitions {
//    Calculator calculator = new Calculator();
    @Inject
    World world;

    @Given("entry of the number {int}")
    public void entry_of_the_number(int n) {
        world.calculator.enter(n);
    }

    @When("add is pressed with a value {int}")
    public void add_is_pressed_with_a_value(int n) {
        world.calculator.add(n);
    }

    @Then("the calculator has the value {int}")
    public void the_calculator_has_the_value(int n) {
        assertThat(world.calculator.value()).isEqualTo(n);
    }
}

