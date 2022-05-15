package com.langrsoft.reporting;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

class FileReportTest {
    @Test
    void canLoadInputStreamIntoTitleAndContentArray() {
        // First try instantiating FileReport
        // Then use Expose Static Method to directly test the load method
        // Test:
        // Given a buffered reader,
        // When load is called (statically)
        // Then it should be a 2-element array with first line + rest of lines
    }

    private BufferedReader bufferedReaderOn(String... lines) {
        return toBufferedReader(withEOLs(lines));
    }

    private BufferedReader toBufferedReader(String s) {
        ByteArrayInputStream stream = new ByteArrayInputStream(s.getBytes());
        return new BufferedReader(new InputStreamReader(stream));
    }

    private String withEOLs(String... lines) {
        return String.join(System.lineSeparator(), lines) + System.lineSeparator();
    }

}
