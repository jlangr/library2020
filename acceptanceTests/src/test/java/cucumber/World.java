package cucumber;

//import io.cucumber.guice.ScenarioScoped;
import library.LibraryClient;
import library.ScannerClient;
import util.Point;

//@ScenarioScoped
public class World {
    public LibraryClient libraryClient = new LibraryClient();
    public ScannerClient scannerClient = new ScannerClient(libraryClient);
    public int checkoutResponse;
}
