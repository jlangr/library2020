package reporting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportWriter {
    void writeReport(String result, String filename) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write("Inventory Report");
            writer.write(InventoryReport.NEWLINE); // TODO replicate
            writer.write(result, 0, result.length());
        } finally {
            writer.close();
        }
    }
}
