package cucumber;

import io.cucumber.guice.ScenarioScoped;
import library.LibraryClient;
import library.ScannerClient;

@ScenarioScoped
public class World {
    public LibraryClient libraryClient = new LibraryClient();
    public ScannerClient scannerClient = new ScannerClient();
    public int checkoutResponse;
}
