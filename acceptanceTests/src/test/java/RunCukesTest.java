
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @ignore",
        plugin = {"pretty"},
        // TODO I should not need to have acceptanceTests here,
        // shouldn't it reference the path from the sub-project?
        features = "acceptanceTests/src/test/resources",
//        features = "acceptanceTests/src/test/resources/library/scanstation.feature",
        monochrome = true)
public class RunCukesTest {
}
