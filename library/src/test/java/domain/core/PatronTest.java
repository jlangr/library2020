package domain.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.EqualityTester;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static util.matchers.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

public class PatronTest {
    private Patron jane;

    @BeforeEach
    public void initialize() {
        jane = new Patron("Jane");
    }

    @Test
    public void defaultsIdToEmpty() {
        assertThat(jane.getId(), equalTo(""));
    }

    @Test
    public void fineBalanceIsZeroOnCreation() {
        assertThat(jane.fineBalance(), equalTo(0));
    }

    @Test
    public void holdingsAreEmptyOnCreation() {
        assertThat(jane.holdingMap().isEmpty(), equalTo(true));
    }

    @Test
    public void returnsHoldingsAdded() {
        var holding = new HoldingBuilder().create();

        jane.add(holding);

        assertThat(jane.holdingMap().holdings(), hasExactlyItemsInAnyOrder(holding));
    }

    @Test
    public void removesHoldingFromPatron() {
        var holding = new HoldingBuilder().create();
        jane.add(holding);

        jane.remove(holding);

        assertThat(jane.holdingMap().isEmpty(), equalTo(true));
    }

    @Test
    public void storesFines() {
        jane.addFine(10);

        assertThat(jane.fineBalance(), equalTo(10));
    }

    @Test
    public void increasesBalanceOnAdditionalFines() {
        jane.addFine(10);

        jane.addFine(30);

        assertThat(jane.fineBalance(), equalTo(40));
    }

    @Test
    public void decreasesBalanceWhenPatronRemitsAmount() {
        jane.addFine(40);

        jane.remit(25);

        assertThat(jane.fineBalance(), equalTo(15));
    }

    @Test
    public void supportsEqualityComparison() {
        var patron1 = new Patron("p1", "Joe");
        var patron1Copy1 = new Patron("p1", "");
        var patron1Copy2 = new Patron("p1", "");
        var patron1Subtype = new Patron("p1", "") {
        };
        var patron2 = new Patron("p2", "");

        new EqualityTester(patron1, patron1Copy1, patron1Copy2, patron2, patron1Subtype).verify();
    }
}
