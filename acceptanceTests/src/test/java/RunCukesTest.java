
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @ignore",
        plugin = {"pretty"},
        features = "acceptanceTests/src/test/resources",
//        features = "acceptanceTests/src/test/resources/library/scanstation.feature",
        monochrome = true)
public class RunCukesTest {
}
