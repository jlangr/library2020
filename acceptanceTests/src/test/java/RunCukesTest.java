
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
   tags="not @ignore",
   plugin={"pretty"},
   features="classpath:.",
//   features="./src/test/resources", // works for root gradle but not within IDEA
//   features="acceptanceTests/src/test/resources", // works within IDEA but not using root gradle
   monochrome=true)
public class RunCukesTest {
}
