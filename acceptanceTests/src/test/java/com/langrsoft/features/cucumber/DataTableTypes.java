package com.langrsoft.features.cucumber;

import com.langrsoft.controller.BranchRequest;
import com.langrsoft.controller.HoldingResponse;
import com.langrsoft.controller.MaterialRequest;
import com.langrsoft.controller.PatronRequest;
import io.cucumber.java.DataTableType;
import com.langrsoft.features.library.BranchRequestBuilder;
import com.langrsoft.features.library.HoldingResponseBuilder;
import com.langrsoft.features.library.MaterialRequestBuilder;
import com.langrsoft.features.library.PatronRequestBuilder;

import java.util.Map;

public class DataTableTypes {
    @DataTableType
    public BranchRequest branchRequest(Map<String, String> tableEntry) {
        return new BranchRequestBuilder()
                .id(tableEntry.get("id")) // note the space!
                .name(tableEntry.get("name"))
                .build();
    }

    @DataTableType
    public MaterialRequest materialRequest(Map<String, String> tableEntry) {
        return new MaterialRequestBuilder()
                .sourceId(tableEntry.get("source id")) // note the space!
                .classification(tableEntry.get("classification"))
                .format(tableEntry.get("format"))
                .build();
    }

    @DataTableType
    public PatronRequest patronRequest(Map<String, String> tableEntry) {
        return new PatronRequestBuilder()
                .id(tableEntry.get("id"))
                .name(tableEntry.get("name"))
                .fineBalance((Integer.parseInt(tableEntry.get("fine balance"))))
                .build();
    }

    @DataTableType
    public HoldingResponse holdingResponse(Map<String, String> tableEntry) {
        return new HoldingResponseBuilder()
                .barcode(tableEntry.get("barcode"))
                .build();
    }
}
