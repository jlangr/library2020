package com.langrsoft.external;

import org.springframework.web.client.RestTemplate;
import com.langrsoft.util.RestUtil;

public class ClassificationService implements ClassificationApi {
    RestTemplate restTemplate = RestUtil.createRestTemplate();
    OpenLibraryApiClient openLibraryApiClient = new OpenLibraryApiClient(restTemplate);

    @Override
    public Material retrieveMaterial(String sourceId) {
        var bookData = openLibraryApiClient.retrieveBookData(sourceId);
        return createMaterial(sourceId, bookData);
    }

    private Material createMaterial(String sourceId, OpenLibraryBookData bookData) {
        var material = new Material();
        material.setSourceId(sourceId);
        material.setFormat(MaterialType.BOOK);
        material.setTitle(bookData.getTitle());
        material.setYear(bookData.getPublishDate());
        material.setAuthor(bookData.getFirstAuthorName());
        material.setClassification(bookData.getLibraryOfCongressClassification());
        return material;
    }
}