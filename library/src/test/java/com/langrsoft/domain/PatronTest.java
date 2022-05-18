package com.langrsoft.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.EqualityTester;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static testutil.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

class PatronTest {
    private Patron jane;

    @BeforeEach
    void initialize() {
        jane = new Patron("Jane");
    }

    @Test
    void defaultsIdToEmpty() {
        assertThat(jane.getId(), equalTo(""));
    }

    @Test
    void fineBalanceIsZeroOnCreation() {
        assertThat(jane.fineBalance(), equalTo(0));
    }

    @Test
    void holdingsAreEmptyOnCreation() {
        assertThat(jane.holdingMap().isEmpty(), equalTo(true));
    }

    @Test
    void returnsHoldingsAdded() {
        var holding = new HoldingBuilder().build();

        jane.add(holding);

        assertThat(jane.holdingMap().holdings(), hasExactlyItemsInAnyOrder(holding));
    }

    @Test
    void removesHoldingFromPatron() {
        var holding = new HoldingBuilder().build();
        jane.add(holding);

        jane.remove(holding);

        assertThat(jane.holdingMap().isEmpty(), equalTo(true));
    }

    @Test
    void storesFines() {
        jane.addFine(10);

        assertThat(jane.fineBalance(), equalTo(10));
    }

    @Test
    void increasesBalanceOnAdditionalFines() {
        jane.addFine(10);

        jane.addFine(30);

        assertThat(jane.fineBalance(), equalTo(40));
    }

    @Test
    void decreasesBalanceWhenPatronRemitsAmount() {
        jane.addFine(40);

        jane.remit(25);

        assertThat(jane.fineBalance(), equalTo(15));
    }

    @Test
    void supportsEqualityComparison() {
        var patron1 = new Patron("p1", "Joe");
        var patron1Copy1 = new Patron("p1", "");
        var patron1Copy2 = new Patron("p1", "");
        var patron1Subtype = new Patron("p1", "") {
        };
        var patron2 = new Patron("p2", "");

        new EqualityTester(patron1, patron1Copy1, patron1Copy2, patron2, patron1Subtype).verify();
    }
}
