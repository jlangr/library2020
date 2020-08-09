
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @ignore",
        plugin = {"pretty"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = "acceptanceTests/src/test/resources",
        monochrome = true)
public class RunCukesTest {
}
