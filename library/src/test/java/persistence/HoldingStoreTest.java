package persistence;

import domain.core.Holding;
import domain.core.HoldingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static testutil.CollectionsUtil.soleElement;

public class HoldingStoreTest {
    private HoldingStore store;
    private Holding savedHolding;

    @BeforeEach
    public void setUp() {
        HoldingStore.deleteAll();
        store = new HoldingStore();
        savedHolding = new HoldingBuilder().create();
        store.save(savedHolding);
    }

    @Test
    public void returnsAddedHoldings() {
        var retrieved = store.findByClassification(classification(savedHolding));

        assertThat(soleElement(retrieved).getMaterial(), equalTo(savedHolding.getMaterial()));
    }

    private String classification(Holding holding) {
        return holding.getMaterial().getClassification();
    }

    @Test
    public void returnsNewInstanceOnRetrieval() {
        store = new HoldingStore();

        var retrieved = store.findByClassification(classification(savedHolding));

        assertThat(soleElement(retrieved), not(sameInstance(savedHolding)));
    }

    @Test
    public void findByBarCodeReturnsMatchingHolding() {
        var holding = store.findByBarcode(savedHolding.getBarcode());

        assertThat(holding.getBarcode(), equalTo(savedHolding.getBarcode()));
    }

    @Test
    public void findBarCodeNotFound() {
        assertThat(store.findByBarcode("nonexistent barcode:1"), nullValue());
    }
}
