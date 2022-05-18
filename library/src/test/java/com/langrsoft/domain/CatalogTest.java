package com.langrsoft.domain;

import com.langrsoft.external.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.langrsoft.persistence.HoldingStore;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static testutil.HasExactlyItems.hasExactlyItems;

class CatalogTest {
    Catalog catalog = new Catalog();
    HoldingBuilder holdingBuilder = new HoldingBuilder();

    @BeforeEach
    void initialize() {
        HoldingStore.deleteAll();
    }

    @Test
    void isEmptyOnCreation() {
        assertThat(catalog.size(), equalTo(0));
    }

    @Test
    void incrementsSizeWhenMaterialAdded() {
        catalog.add(holdingBuilder.build());

        assertThat(catalog.size(), equalTo(1));
    }

    @Test
    void answersEmptyForNonexistentMaterial() {
        assertThat(catalog.findAll("nonexistentid").isEmpty(), equalTo(true));
    }

    @Test
    void findAllReturnsListOfHoldings() {
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
        var holding = holdingBuilder.material(material).build();
        return catalog.add(holding);
    }

    @Test
    void findAllReturnsOnlyHoldingsWithMatchingClassification() {
        var barcode1 = addHoldingWithClassification("123");
        addHoldingWithClassification("456");

        var retrieved = catalog.findAll("123");

        assertThat(retrieved.size(), equalTo(1));
        assertThat(retrieved.get(0).getBarcode(), equalTo(barcode1));
    }

    @Test
    void retrievesHoldingUsingBarcode() {
        var holding = holdingBuilder.build();
        var barcode = catalog.add(holding);

        var retrieved = catalog.find(barcode);

        assertThat(retrieved, equalTo(holding));
    }

    @Test
    void incrementsCopyNumberWhenSameClassificationExists() {
        var holding = holdingBuilder.build();
        catalog.add(holding);
        var barcode = catalog.add(holding);

        var retrieved = catalog.find(barcode);

        assertThat(retrieved.getCopyNumber(), equalTo(2));
    }

    @Test
    void supportsIteration() {
        var barcode1 = addHoldingWithClassification("1");
        var barcode2 = addHoldingWithClassification("2");

        var results = new ArrayList<>();
        for (var holding : catalog)
            results.add(holding.getBarcode());

        assertThat(results, hasExactlyItems(barcode1, barcode2));
    }
}
