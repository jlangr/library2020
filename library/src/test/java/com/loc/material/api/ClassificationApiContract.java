package com.loc.material.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.experimental.categories.Category;
import testutil.Slow;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Category(Slow.class)
public abstract class ClassificationApiContract {
    private Material material;

    @BeforeEach
    public void createAndRetrieve() {
        ClassificationApi classificationApiImplementation = createClassificationApiImpl();
        material = classificationApiImplementation.retrieveMaterial(validQueryIsbn());
    }

    @Test
    public void populatesCriticalFields() {
        assertThat(material.getAuthor(), not(nullValue()));
        assertThat(material.getTitle(), not(nullValue()));
        assertThat(material.getClassification(), not(nullValue()));
    }

    @Test
    public void echosSourceId() {
        assertThat(material.getSourceId(), is(equalTo(validQueryIsbn())));
    }

    @Test
    public void populatesFormatWithEnumValue() {
        assertThat(material.getFormat(), isIn(MaterialType.values()));
    }

    @Test
    public void populatesYearWithReasonableValue() {
        var currentYear = LocalDate.now().getYear();
        assertThat(Integer.parseInt(material.getYear()),
                is(both(greaterThan(1440)).and(lessThanOrEqualTo(currentYear))));
    }


    abstract protected ClassificationApi createClassificationApiImpl();

    abstract protected String validQueryIsbn();
}
