package api.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.PatronStore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatronServiceTest {
    PatronService service;

    @BeforeEach
    public void initialize() {
        PatronStore.deleteAll();
        service = new PatronService();
    }

    @Test
    public void answersGeneratedId() {
        var scanCode = service.add("name");

        assertThat(scanCode, startsWith("p"));
    }

    @Test
    public void allowsAddingPatronWithId() {
        service.add("p123", "xyz");

        var patron = service.find("p123");

        assertThat(patron.getName(), equalTo("xyz"));
    }

    @Test
    public void rejectsPatronIdNotStartingWithP() {
        assertThrows(InvalidPatronIdException.class, () ->
                service.add("234", ""));
    }

    @Test
    public void rejectsAddOfDuplicatePatron() {
        service.add("p556", "");
        assertThrows(DuplicatePatronException.class, () ->
            service.add("p556", ""));
    }

    @Test
    public void answersNullWhenPatronNotFound() {
        assertThat(service.find("nonexistent id"), nullValue());
    }
}
