package com.langrsoft.external;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import com.langrsoft.util.RestUtil;

import java.util.Map;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenLibraryApiClientTest {
    private static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
    private static final String THE_ROAD_ISBN = "0-307-26543-9";
    private static final String THE_ROAD_TITLE = "The Road";
    private static final String THE_ROAD_YEAR = "2006";
    private static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    OpenLibraryApiClient openLibraryApiClient;

    @Test
    void retrieveMaterialPopulatesFromResponse() {
        var bookDataMap = Map.of("title", THE_ROAD_TITLE,
                "publish_date", THE_ROAD_YEAR,
                "classifications", Map.of("lc_classifications", singletonList(THE_ROAD_CLASSIFICATION)),
                "authors", singletonList(Map.of("name", THE_ROAD_AUTHOR)));
        var responseMap = Map.of("ISBN:" + THE_ROAD_ISBN, bookDataMap);
        var expectedUrl = openLibraryApiClient.createGetUrl(THE_ROAD_ISBN);
        when(restTemplate.getForObject(expectedUrl, Map.class))
                .thenReturn(responseMap);

        var bookData = openLibraryApiClient.retrieveBookData(THE_ROAD_ISBN);

        assertThat(bookData.getTitle(), equalTo(THE_ROAD_TITLE));
        assertThat(bookData.getPublishDate(), equalTo(THE_ROAD_YEAR));
        assertThat(bookData.getFirstAuthorName(), equalTo(THE_ROAD_AUTHOR));
        assertThat(bookData.getLibraryOfCongressClassification(), equalTo(THE_ROAD_CLASSIFICATION));
    }

    @Test
    void retrieveMaterialThrowsWhenResponseIsNull() {
        var expectedUrl = openLibraryApiClient.createGetUrl(THE_ROAD_ISBN);
        when(restTemplate.getForObject(expectedUrl, Map.class)).thenReturn(null);

        assertThrows(OpenLibraryApiRetrieveException.class, () ->
                openLibraryApiClient.retrieveBookData(THE_ROAD_ISBN));
    }

    @Tag("slow")
    @Test
    void liveRetrieve() {
        var client = new OpenLibraryApiClient(RestUtil.createRestTemplate());

        var bookData = client.retrieveBookData(THE_ROAD_ISBN);

        assertThat(bookData.getTitle(), equalTo(THE_ROAD_TITLE));
        assertThat(bookData.getPublishDate(), equalTo(THE_ROAD_YEAR));
        assertThat(bookData.getFirstAuthorName(), equalTo(THE_ROAD_AUTHOR));
        assertThat(bookData.getLibraryOfCongressClassification(), equalTo(THE_ROAD_CLASSIFICATION));
    }
}