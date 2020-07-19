package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import util.Billing;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class BillingStepdefs {

    private List<Double> charges;

    @Given("the customer has the following monthly charges:")
    public void the_customer_has_the_following_monthly_charges(List<Double> charges) {
        this.charges = charges;
    }

    @Then("the average monthly billing will be {double}")
    public void the_average_monthly_billing_will_be(double expected) {
        assertThat(new Billing().monthlyBillingAverage(charges))
                .isCloseTo(expected, offset(0.005));
    }
}
