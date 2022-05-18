package com.langrsoft.reporting;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class FileReportTest {
    @Disabled("uncomment when ready")
    @Test
    void canLoadInputStreamIntoTitleAndContentArray() {
        new FileReport("filename");
        // First try instantiating FileReport
        // Then use Expose Static Method to directly test the load method
        // Test:
        // Given a buffered reader,
        // When load is called (statically)
        // Then it should be a 2-element array with first line + rest of lines

        assertThat(true, equalTo(true));
    }

    // TODO move to IO util class and add test
    private BufferedReader bufferedReaderOn(String... lines) {
        return toBufferedReader(withEOLs(lines));
    }

    private BufferedReader toBufferedReader(String s) {
        var stream = new ByteArrayInputStream(s.getBytes());
        return new BufferedReader(new InputStreamReader(stream));
    }

    private String withEOLs(String... lines) {
        return String.join(System.lineSeparator(), lines) + System.lineSeparator();
    }

}
