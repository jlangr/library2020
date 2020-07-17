package library;

import api.scanner.ScanStation;
import com.nssi.devices.model1801c.ScanDisplayListener;

public class ScannerClient implements ScanDisplayListener {

    private ScanDisplayListener display = this;
    ScanStation scanner = new ScanStation(display);

    @Override
    public void showMessage(String text) {

    }

    public void scanBarcode(String barcodeForBranch) {
        scanner.scan(barcodeForBranch);
    }

    public String barcodeForBranch(String branchName) {
        return "";
    }
}
