package com.langrsoft.service.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.langrsoft.persistence.PatronStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PatronServiceTest {
    PatronService service;

    @BeforeEach
    void initialize() {
        PatronStore.deleteAll();
        service = new PatronService();
    }

    @Test
    void answersGeneratedId() {
        var scanCode = service.add("name");

        assertThat(scanCode, startsWith("p"));
    }

    @Test
    void allowsAddingPatronWithId() {
        service.add("p123", "xyz");

        var patron = service.find("p123");

        assertThat(patron.getName(), equalTo("xyz"));
    }

    @Test
    void rejectsPatronIdNotStartingWithP() {
        assertThrows(InvalidPatronIdException.class, () ->
                service.add("234", ""));
    }

    @Test
    void rejectsAddOfDuplicatePatron() {
        service.add("p556", "");
        assertThrows(DuplicatePatronException.class, () ->
            service.add("p556", ""));
    }

    @Test
    void answersNullWhenPatronNotFound() {
        assertThat(service.find("nonexistent id"), nullValue());
    }
}
