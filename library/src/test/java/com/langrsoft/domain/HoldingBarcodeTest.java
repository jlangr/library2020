package com.langrsoft.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HoldingBarcodeTest {
    @Test
    void splitsBarcodeProvidedIntoCopyAndClassification() {
        var barcode = new HoldingBarcode("ABC:42");

        assertThat(barcode.getClassification(), equalTo("ABC"));
        assertThat(barcode.getCopyNumber(), equalTo(42));
    }

    @Test
    void barcode_causesThrowWhenNoColonExists() {
        assertThrows(IllegalArgumentException.class, () -> new HoldingBarcode("abc"));
    }

    @Test
    void barcode_causesThrowWhenCopyNumberNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> new HoldingBarcode("abc:-124"));
    }

    @Test
    void barcode_causesThrowWhenCopyNumberNotPositiveInt() {
        assertThrows(IllegalArgumentException.class, () -> new HoldingBarcode("abc:xyz"));
    }


    @Nested
    class EqualsAndHashcode {
        HoldingBarcode barcode = new HoldingBarcode("A", 1);
        HoldingBarcode barcode1copy = new HoldingBarcode("A", 1);
        HoldingBarcode barcode2 = new HoldingBarcode("B", 1);

        @Test
        void usesHashCode() {
            assertThat(barcode.hashCode(), equalTo(barcode1copy.hashCode()));
            assertThat(barcode.hashCode(), not(equalTo(barcode2.hashCode())));
        }

        @Test
        void equality() {
            assertThat(barcode.equals(barcode1copy), is(true));
            assertThat(barcode.equals(barcode2), is(false));
        }
    }

    @Test
    void canBeConstructedFromClassificatonAndCopy() {
        var barcode = new HoldingBarcode("ABC", 42);

        assertThat(barcode.getClassification(), equalTo("ABC"));
        assertThat(barcode.getCopyNumber(), equalTo(42));
    }

    @Test
    void canDeriveCopyNumberFromBarcode() {
        assertThat(HoldingBarcode.getCopyNumber("QA123:2"),
               equalTo(2));
    }

    @Test
    void canDeriveClassificationFromBarcode() {
        assertThat(HoldingBarcode.getClassification("QA123:2"),
                equalTo("QA123"));
    }

    @Test
    void usesBarcodeForToString() {
        var barcode = HoldingBarcode.createCode("QA123", 20);
        assertThat(barcode, equalTo("QA123:20"));
        assertThat(new HoldingBarcode("QA123:20").toString(), equalTo(barcode));
    }


}
