package com.langrsoft.service.scanner;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class BarcodeInterpreterTest {
    @Test
    void returnsHoldingTypeWhenBarcodeContainsColon() {
        assertThat(BarcodeInterpreter.typeOf("123:1"), equalTo(BarcodeType.HOLDING));
    }

    @Test
    void returnsBranchTypeWhenStartsWithB() {
        assertThat(BarcodeInterpreter.typeOf("b123"), equalTo(BarcodeType.BRANCH));
        assertThat(BarcodeInterpreter.typeOf("B123"), equalTo(BarcodeType.BRANCH));
    }

    @Test
    void returnsInventoryTypeWhenStartsWithI() {
        assertThat(BarcodeInterpreter.typeOf("i111"), equalTo(BarcodeType.INVENTORY));
        assertThat(BarcodeInterpreter.typeOf("I111"), equalTo(BarcodeType.INVENTORY));
    }

    @Test
    void returnsPatronTypeWhenStartsWithP() {
        assertThat(BarcodeInterpreter.typeOf("p123"), equalTo(BarcodeType.PATRON));
    }

    @Test
    void returnsUnrecognizedTypeWhenOther() {
        assertThat(BarcodeInterpreter.typeOf("123"), equalTo(BarcodeType.UNRECOGNIZED));
    }
}
