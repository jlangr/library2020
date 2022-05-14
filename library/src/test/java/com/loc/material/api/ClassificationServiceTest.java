package com.loc.material.api;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import testutil.Slow;

import java.util.Map;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassificationServiceTest {
    private static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
    private static final String THE_ROAD_ISBN = "0-307-26543-9";
    private static final String THE_ROAD_TITLE = "The Road";
    private static final String THE_ROAD_YEAR = "2006";
    private static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ClassificationService service;

    @Test
    void retrieveMaterialPopulatesFromResponse() {
        var responseMap = Map.of(service.isbnKey(THE_ROAD_ISBN),
                Map.of("title", THE_ROAD_TITLE,
                        "publish_date", THE_ROAD_YEAR,
                        "classifications", Map.of("lc_classifications", singletonList(THE_ROAD_CLASSIFICATION)),
                        "authors", singletonList(Map.of("name", THE_ROAD_AUTHOR))));
        when(restTemplate.getForObject(contains(THE_ROAD_ISBN), eq(Map.class))).thenReturn(responseMap);

        var material = service.retrieveMaterial(THE_ROAD_ISBN);

        assertMaterialDetailsForTheRoad(material);
    }

    @Category(Slow.class)
    @Test
    void liveRetrieve() {
        var liveService = new ClassificationService();

        var material = liveService.retrieveMaterial(THE_ROAD_ISBN);

        assertMaterialDetailsForTheRoad(material);
    }

    private void assertMaterialDetailsForTheRoad(Material material) {
        assertThat(material.getTitle(), equalTo(THE_ROAD_TITLE));
        assertThat(material.getYear(), equalTo(THE_ROAD_YEAR));
        assertThat(material.getAuthor(), equalTo(THE_ROAD_AUTHOR));
        assertThat(material.getSourceId(), equalTo(THE_ROAD_ISBN));
        assertThat(material.getClassification(), equalTo(THE_ROAD_CLASSIFICATION));
    }
}
