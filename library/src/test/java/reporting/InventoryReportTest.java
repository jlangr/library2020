package reporting;

import com.loc.material.api.Material;
import domain.core.Catalog;
import domain.core.Holding;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InventoryReportTest {
    @Test
    public void shouldConstruct() throws IOException {
        Catalog catalog = new Catalog();
        catalog.add(new Holding(new Material("id", "Kafka", "The Trial", "QA123", "1925")));
        var report = new InventoryReport(catalog, new LibraryOfCongress() {
            @Override public String getISBN(String classification) { return "12345"; }
        });
        report.reportWriter = mock(ReportWriter.class);

        report.allBooks();

        var expectedReport =
        "Title                 Branch                Author                          Year  ISBN      " + InventoryReport.NEWLINE +
        "--------------------  --------------------  ------------------------------  ------  " + InventoryReport.NEWLINE +
        "The Trial             unavailable           Kafka                           1925    12345     " + InventoryReport.NEWLINE;
        verify(report.reportWriter).writeReport(expectedReport, InventoryReport.OUTPUT_FILENAME);
    }
}