package com.langrsoft.features.cucumber;

//import io.cucumber.guice.ScenarioScoped;
import com.langrsoft.features.library.LibraryClient;
import com.langrsoft.features.library.ScannerClient;

//@ScenarioScoped
public class World {
    public LibraryClient libraryClient = new LibraryClient();
    public ScannerClient scannerClient = new ScannerClient(libraryClient);
    public int checkoutResponse;
}
