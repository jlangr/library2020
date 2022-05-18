package com.langrsoft.controller;

import com.langrsoft.domain.HoldingBuilder;
import com.langrsoft.external.Material;
import com.langrsoft.external.MaterialType;
import com.langrsoft.util.DateUtil;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static testutil.AllItemsAllFieldsEqual.allItemsAllFieldsEqual;

class HoldingResponseTest {

    @Test
    void createFromHolding() {
        var material = new Material(
                "source", "author", "title", "classif",
                MaterialType.BOOK, "1999");
        var checkoutDate = DateUtil.create(2023, 2, 1);
        var holding = new HoldingBuilder()
                .copyNumber(42)
                .material(material)
                .classification("QA123")
                .checkout(checkoutDate)
                .build();

        var holdingResponse = new HoldingResponse(holding);

        assertThat(holdingResponse.getBarcode(), equalTo("QA123:42"));
        assertThat(holdingResponse.getCopyNumber(), equalTo(42));
        assertThat(holdingResponse.getDateCheckedOut(), equalTo(checkoutDate));
        assertThat(holdingResponse.getDateDue(), equalTo(holding.dateDue()));
        assertThat(holdingResponse.getIsAvailable(), equalTo(false));
        assertThat(holdingResponse.getDateLastCheckedIn(), equalTo(null));
        assertThat(holdingResponse.getAuthor(), equalTo("author"));
        assertThat(holdingResponse.getTitle(), equalTo("title"));
        assertThat(holdingResponse.getYear(), equalTo("1999"));
        assertThat(holdingResponse.getFormat(), equalTo("BOOK"));
    }

    @Test
    void createFromHoldings() {
        var holding1 = new HoldingBuilder().build();
        var holding2 = new HoldingBuilder().build();

        var holdingResponses = HoldingResponse.create(asList(holding1, holding2));

        assertThat(holdingResponses,
                allItemsAllFieldsEqual(asList(new HoldingResponse(holding1), new HoldingResponse(holding2))));
    }
}
