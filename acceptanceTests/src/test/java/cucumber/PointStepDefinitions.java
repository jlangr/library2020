package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.Point;

import static org.assertj.core.api.Assertions.assertThat;

public class PointStepDefinitions {
    private Point point;

    @Given("a point {point}")
    public void a_point(Point point) {
        System.out.println("-> " + ParameterTypes.POINT_REGEX);
        this.point = point;
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
