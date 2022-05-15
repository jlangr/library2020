package com.langrsoft.external;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Tag("slow")
abstract class ClassificationApiContract {
    private Material material;

    @BeforeEach
    void createAndRetrieve() {
        ClassificationApi classificationApiImplementation = createClassificationApiImpl();
        material = classificationApiImplementation.retrieveMaterial(validQueryIsbn());
    }

    @Test
    void populatesCriticalFields() {
        assertThat(material.getAuthor(), not(nullValue()));
        assertThat(material.getTitle(), not(nullValue()));
        assertThat(material.getClassification(), not(nullValue()));
    }

    @Test
    void echosSourceId() {
        assertThat(material.getSourceId(), is(equalTo(validQueryIsbn())));
    }

    @Test
    void populatesFormatWithEnumValue() {
        assertThat(material.getFormat(), isIn(MaterialType.values()));
    }

    @Test
    void populatesYearWithReasonableValue() {
        var currentYear = LocalDate.now().getYear();
        assertThat(Integer.parseInt(material.getYear()),
                is(both(greaterThan(1440)).and(lessThanOrEqualTo(currentYear))));
    }


    abstract protected ClassificationApi createClassificationApiImpl();

    abstract protected String validQueryIsbn();
}
