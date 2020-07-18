package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.Point;

import static org.assertj.core.api.Assertions.assertThat;

public class PointStepDefinitions {
    private Point point;

    @Given("a point with x of {double} and y of {double}")
    public void a_point_with_x_of_and_y_of(double x, double y) {
        point = new Point(x, y);
    }

    @When("moving {double} with an angle of {double} degrees")
    public void moving_with_an_angle_of_degrees(double distance, double degrees) {
        point = point.move(distance, degrees);
    }

    @Then("the new point has an x of {double} and a y of {double}")
    public void the_new_point_has_an_x_of_and_a_y_of(double x, double y) {
        assertThat(point).isEqualTo(new Point(x, y));
    }
}
