package com.langrsoft.external;

import java.util.List;
import java.util.Map;

class OpenLibraryBookData {
    private final Map<String, Object> bookData;

    OpenLibraryBookData(Map<String, Object> bookData) {
        this.bookData = bookData;
    }

    public String getFirstAuthorName() {
        var firstAuthor = getFirstMap(getList(bookData, "authors"));
        return (String)firstAuthor.get("name");
    }

    public String getTitle() {
        return getString("title");
    }

    public String getPublishDate() {
        return getString("publish_date");
    }

    public String getLibraryOfCongressClassification() {
        @SuppressWarnings("unchecked")
        var classifications = (Map<String, Object>)bookData.get("classifications");
        var libraryOfCongressClassifications = getList(classifications, "lc_classifications");
        return (String)libraryOfCongressClassifications.get(0);
    }

    private String getString(String key) {
        return (String)bookData.get(key);
    }

    @SuppressWarnings("unchecked")
    private List<Object> getList(Map<String, Object> map, String key) {
        return (List<Object>)map.get(key);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getFirstMap(List<Object> list) {
        return (Map<String, Object>)list.get(0);
    }
}
