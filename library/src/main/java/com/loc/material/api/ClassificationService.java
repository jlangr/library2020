package com.loc.material.api;

import org.springframework.web.client.RestTemplate;
import util.RestUtil;

public class ClassificationService implements ClassificationApi {
    private RestTemplate restTemplate = RestUtil.createRestTemplate();
    private OpenLibraryApiClient openLibraryApiClient = new OpenLibraryApiClient(restTemplate);

    @Override
    public Material retrieveMaterial(String sourceId) {
        var bookData = openLibraryApiClient.retrieveBookData(sourceId);
        return createMaterial(sourceId, bookData);
    }

    private Material createMaterial(String sourceId, OpenLibraryBookData bookData) {
        var material = new Material();
        material.setSourceId(sourceId);
        material.setFormat(MaterialType.Book);
        material.setTitle(bookData.getTitle());
        material.setYear(bookData.getPublishDate());
        material.setAuthor(bookData.getFirstAuthorName());
        material.setClassification(bookData.getLibraryOfCongressClassification());
        return material;
    }
}