package domain.core;

import com.loc.material.api.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.HoldingStore;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static util.matchers.HasExactlyItems.hasExactlyItems;

public class CatalogTest {
    private Catalog catalog = new Catalog();
    private HoldingBuilder holdingBuilder = new HoldingBuilder();

    @BeforeEach
    public void initialize() {
        HoldingStore.deleteAll();
    }

    @Test
    public void isEmptyOnCreation() {
        assertThat(catalog.size(), equalTo(0));
    }

    @Test
    public void incrementsSizeWhenMaterialAdded() {
        catalog.add(holdingBuilder.create());

        assertThat(catalog.size(), equalTo(1));
    }

    @Test
    public void answersEmptyForNonexistentMaterial() {
        assertThat(catalog.findAll("nonexistentid").isEmpty(), equalTo(true));
    }

    @Test
    public void findAllReturnsListOfHoldings() {
        var classification = "123";
        var barcode = addHoldingWithClassification(classification);
        var barcode2 = addHoldingWithClassification(classification);

        var holdings = catalog.findAll(classification);

        var retrieved1 = catalog.find(barcode);
        var retrieved2 = catalog.find(barcode2);
        assertThat(holdings, equalTo(asList(retrieved1, retrieved2)));
    }

    private String addHoldingWithClassification(String classification) {
        var material = new Material("", "", "", classification, "");
        var holding = holdingBuilder.with(material).create();
        return catalog.add(holding);
    }

    @Test
    public void findAllReturnsOnlyHoldingsWithMatchingClassification() {
        var barcode1 = addHoldingWithClassification("123");
        addHoldingWithClassification("456");

        var retrieved = catalog.findAll("123");

        assertThat(retrieved.size(), equalTo(1));
        assertThat(retrieved.get(0).getBarcode(), equalTo(barcode1));
    }

    @Test
    public void retrievesHoldingUsingBarcode() {
        var holding = holdingBuilder.create();
        var barcode = catalog.add(holding);

        var retrieved = catalog.find(barcode);

        assertThat(retrieved, equalTo(holding));
    }

    @Test
    public void incrementsCopyNumberWhenSameClassificationExists() {
        var holding = holdingBuilder.create();
        catalog.add(holding);
        var barcode = catalog.add(holding);

        var retrieved = catalog.find(barcode);

        assertThat(retrieved.getCopyNumber(), equalTo(2));
    }

    @Test
    public void supportsIteration() {
        String barcode1 = addHoldingWithClassification("1");
        String barcode2 = addHoldingWithClassification("2");

        var results = new ArrayList<>();
        for (var holding : catalog)
            results.add(holding.getBarcode());

        assertThat(results, hasExactlyItems(barcode1, barcode2));
    }
}
