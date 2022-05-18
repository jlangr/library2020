package com.langrsoft.service.library;

import com.langrsoft.persistence.BranchStore;
import com.langrsoft.persistence.HoldingStore;
import com.langrsoft.persistence.PatronStore;

public class LibraryData {
    private LibraryData() {}
    public static void deleteAll() {
        BranchStore.deleteAll();
        HoldingStore.deleteAll();
        PatronStore.deleteAll();
    }
}
