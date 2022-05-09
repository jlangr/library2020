package util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RomanTest {
    private Roman roman;

    @Before
    public void create() {
        roman = new Roman();
    }

    @Test
    public void converts() {
        assertEquals("I", roman.convert(1));
        assertEquals("II", roman.convert(2));
        assertEquals("III", roman.convert(3));
        assertEquals("X", roman.convert(10));
        assertEquals("XI", roman.convert(11));
        assertEquals("XX", roman.convert(20));
        assertEquals("C", roman.convert(100));
        assertEquals("CC", roman.convert(200));
    }
}
