package com.langrsoft.external;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

// This class uses the Open Library API; see https://openlibrary.org/dev/docs/api/books
public class OpenLibraryApiClient {
    public static final String SERVER = "https://openlibrary.org";
    private final RestTemplate restTemplate;

    OpenLibraryApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenLibraryBookData retrieveBookData(String sourceId) {
        var response = get(sourceId);
        if (response == null)
            throw new OpenLibraryApiRetrieveException();
        var bookDataMap = response.get("ISBN:" + sourceId);
        return new OpenLibraryBookData(bookDataMap);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Map<String, Object>> get(String sourceId) {
        try {
            return restTemplate.getForObject(createGetUrl(sourceId), Map.class);
        }
        catch (NullPointerException exception) {
            throw new MaterialNotFoundException(exception);
        }
    }

    String createGetUrl(String sourceId) {
        return String.format(
                "%s/api/books?bibkeys=ISBN:%s&jscmd=data&format=json",
                SERVER, sourceId
        );
    }

}
