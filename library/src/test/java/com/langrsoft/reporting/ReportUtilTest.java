package com.langrsoft.reporting;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.langrsoft.reporting.ReportUtil.transform;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class ReportUtilTest {
    @Nested
    class Transform {
        @Test
        void pad() {
            assertThat(transform("abc", 99, 0, ReportUtil.StringOp.PAD), equalTo("abc"));
            assertThat(transform("abc", 99, 3, ReportUtil.StringOp.PAD), equalTo("abc   "));
        }

        @Test
        void underlines() {
            assertThat(transform("", 5, 0, ReportUtil.StringOp.UNDER), equalTo("-----"));
            assertThat(transform("", 3, 2, ReportUtil.StringOp.UNDER), equalTo("---  "));
        }

    }

}