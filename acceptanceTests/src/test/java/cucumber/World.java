package cucumber;

import io.cucumber.guice.ScenarioScoped;
import library.LibraryClient;
import library.ScannerClient;
import miso.MisoCalculator;
import util.Calculator;

@ScenarioScoped
public class World {
    public LibraryClient libraryClient = new LibraryClient();
    public ScannerClient scannerClient = new ScannerClient(libraryClient);
    public int checkoutResponse;
    public Calculator calculator = new Calculator();
    public MisoCalculator misoCalculator = new MisoCalculator();
}
