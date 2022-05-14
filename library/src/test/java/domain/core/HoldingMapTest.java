package domain.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static util.matchers.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

class HoldingMapTest {
    private HoldingMap map;
    private Holding holding;

    @BeforeEach
    void initialize() {
        map = new HoldingMap();
        holding = new HoldingBuilder().create();
    }

    @Test
    void isEmptyWhenCreated() {
        assertThat(map.isEmpty(), equalTo(true));
    }

    @Test
    void hasSizeZeroWhenCreated() {
        assertThat(map.size(), equalTo(0));
    }

    @Test
    void containsFailsWhenHoldingNotFound() {
        assertThat(map.contains(holding), equalTo(false));
    }

    @Test
    void containsAddedHolding() {
        map.add(holding);

        assertThat(map.contains(holding), equalTo(true));
    }

    @Test
    void sizeIncrementedOnAddingHolding() {
        map.add(holding);

        assertThat(map.size(), equalTo(1));
    }

    @Test
    void retrievesHoldingByBarcode() {
        map.add(holding);

        var retrieved = map.get(holding.getBarcode());

        assertThat(retrieved, sameInstance(holding));
    }

    @Test
    void returnsAllHoldings() {
        var holdingA = new HoldingBuilder().withClassification("a").create();
        var holdingB = new HoldingBuilder().withClassification("b").create();
        map.add(holdingA);
        map.add(holdingB);

        var holdings = map.holdings();

        assertThat(holdings, hasExactlyItemsInAnyOrder(holdingA, holdingB));
    }

    @Test
    void removeHolding() {
        map.add(holding);

        map.remove(holding);

        assertThat(map.contains(holding), equalTo(false)); // TODO way to do this with hamcrest?
    }

    @Test
    void removeHoldingDecrementsSize() {
        map.add(holding);

        map.remove(holding);

        assertThat(map.size(), equalTo(0));
    }

    @Test
    void supportsIteration() {
        var holdingA = new HoldingBuilder().withClassification("a").create();
        var holdingB = new HoldingBuilder().withClassification("b").create();
        map.add(holdingA);
        map.add(holdingB);

        var retrieved = new ArrayList<Holding>();
        for (var holding : map)
            retrieved.add(holding);

        assertThat(retrieved, hasExactlyItemsInAnyOrder(holdingA, holdingB));
    }
}
