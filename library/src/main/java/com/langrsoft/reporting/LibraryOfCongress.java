package com.langrsoft.reporting;

public class LibraryOfCongress {
    public String getISBN(String classification) {
        throw new LibraryOfCongressException(
                String.format("classification: %s not found. Connection currently unavailable, please try later", classification));
    }

}
