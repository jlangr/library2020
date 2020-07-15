package cucumber;

import controller.BranchRequest;
import controller.HoldingResponse;
import controller.MaterialRequest;
import controller.PatronRequest;
import io.cucumber.java.DataTableType;
import library.BranchRequestBuilder;
import library.HoldingResponseBuilder;
import library.MaterialRequestBuilder;
import library.PatronRequestBuilder;

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
