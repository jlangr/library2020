package util;

import net.minidev.json.writer.ArraysMapper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class ARomanConverter {
    private RomanConverter converter = new RomanConverter();

    @Test
    void convertsArabicToRoman() {
        assertThat(converter.convert(1), equalTo("I"));
        assertThat(converter.convert(2), equalTo("II"));
        assertThat(converter.convert(3), equalTo("III"));
        assertThat(converter.convert(10), equalTo("X"));
        assertThat(converter.convert(11), equalTo("XI"));
        assertThat(converter.convert(20), equalTo("XX"));
        assertThat(converter.convert(100), equalTo("C"));
    }
}