package com.langrsoft.reporting;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class ReportMailerTest {
    @Test
    void stub() {
        try {
            new ReportMailer(new MailDestination[] {});
        }
        catch (Exception expected) {
            assertThat(expected, notNullValue());
        }
    }
}
