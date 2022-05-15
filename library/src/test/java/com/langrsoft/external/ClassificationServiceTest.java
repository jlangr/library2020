package com.langrsoft.external;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassificationServiceTest {
    static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
    static final String THE_ROAD_ISBN = "0-307-26543-9";
    static final String THE_ROAD_TITLE = "The Road";
    static final String THE_ROAD_YEAR = "2006";
    static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

    @InjectMocks
    ClassificationService service;
    @Mock
    OpenLibraryApiClient openLibraryApiClient;
    @Mock
    OpenLibraryBookData bookData;

    @Test
    void retrieveMaterialPopulatesFromOpenLibraryBookData() {
        when(bookData.getFirstAuthorName()).thenReturn(THE_ROAD_AUTHOR);
        when(bookData.getLibraryOfCongressClassification()).thenReturn(THE_ROAD_CLASSIFICATION);
        when(bookData.getTitle()).thenReturn(THE_ROAD_TITLE);
        when(bookData.getPublishDate()).thenReturn(THE_ROAD_YEAR);
        when(openLibraryApiClient.retrieveBookData(THE_ROAD_ISBN))
                .thenReturn(bookData);

        var material = service.retrieveMaterial(THE_ROAD_ISBN);

        assertThat(material.getTitle(), equalTo(THE_ROAD_TITLE));
        assertThat(material.getYear(), equalTo(THE_ROAD_YEAR));
        assertThat(material.getAuthor(), equalTo(THE_ROAD_AUTHOR));
        assertThat(material.getSourceId(), equalTo(THE_ROAD_ISBN));
        assertThat(material.getClassification(), equalTo(THE_ROAD_CLASSIFICATION));
    }

}
