package com.langrsoft.service.library;

import com.langrsoft.domain.State;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StateServiceTest {
    StateService service = new StateService();

    @Test
    void findNameStartingWithReturnsEmptyWhenNotFound() {
        assertThat(service.findNameStartingWith("Xyz"), empty());
    }

    @Test
    void findNameStartingWithReturnsSingleElement() {
        assertThat(service.findNameStartingWith("Col"), contains(new State("Colorado", "Denver")));
    }

    @Test
    void findNameStartingWithReturnsMultipleElements() {
        assertThat(service.findNameStartingWith("Te"),
                containsInAnyOrder(new State("Texas", "Austin"), new State("Tennessee", "Nashville")));
    }
}