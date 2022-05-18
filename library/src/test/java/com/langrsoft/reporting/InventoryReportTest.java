package com.langrsoft.reporting;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

class InventoryReportTest {
    @Test
    void something() {
        assertThat(new InventoryReport(null), notNullValue());
    }
}
