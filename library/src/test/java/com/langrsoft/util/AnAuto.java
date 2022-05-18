package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static testutil.LessThan.lessThan;

class AnAuto {
    @Test
    void idlesEngineWhenStarted() {
        var auto = new Auto();
        auto.DepressBrake();

        auto.PressStartButton();

        assertThat(auto.RPM(), is(both(greaterThan(950)).and(lessThan(1100))));
    }

    static class Auto {
        public void DepressBrake() {
        }

        public void PressStartButton() {
        }

        public int RPM() {
            return 1000;
        }
    }
}
