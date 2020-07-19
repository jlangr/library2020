package cucumber;

import controller.BranchRequest;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import library.BranchRequestBuilder;
import util.Plane;
import util.Point;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class PointStepdefs {

    private List<Point> points;

//    @Given("the following points:")
//    public void the_following_points(List<List<String>> points) {
//        this.points = toPointsList(points);
//    }
//
//    private List<Point> toPointsList(List<List<String>> points) {
//        return points.stream()
//                .skip(1)
//                .map(this::createPoint)
//                .collect(toList());
//    }
//
//    private Point createPoint(List<String> row) {
//        return new Point(Double.parseDouble(row.get(0)), Double.parseDouble(row.get(1)));
//    }
//
//    @Then("the closest {int} points are:")
//    public void the_closest_points_are(int k, List<List<String>> expected) {
//        assertThat(new Plane().kClosestPoints(points, k))
//                .isEqualTo(toPointsList(expected));
//    }

    @DataTableType
    public Point point(Map<String, String> tableEntry) {
        return new Point(
                Double.parseDouble(tableEntry.get("x")),
                Double.parseDouble(tableEntry.get("y")));
    }

    @Given("the following points:")
    public void the_following_points(List<Point> points) {
        this.points = points;
    }

    @Then("the closest {int} points are:")
    public void the_closest_points_are(int k, List<Point> expected) {
        assertThat(new Plane().kClosestPoints(points, k))
                .isEqualTo(expected);
    }
}
