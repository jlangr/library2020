package api.scanner;

public class BarcodeInterpreter {
    private BarcodeInterpreter() {}

    public static BarcodeType typeOf(String barcode) {
        if (isBranchId(barcode))
            return BarcodeType.BRANCH;
        if (isInventoryId(barcode))
            return BarcodeType.INVENTORY;
        if (isHoldingId(barcode))
            return BarcodeType.HOLDING;
        if (isPatronId(barcode))
            return BarcodeType.PATRON;
        return BarcodeType.UNRECOGNIZED;
    }

    private static boolean isPatronId(String barcode) {
        return barcode.startsWith("p");
    }

    private static boolean isHoldingId(String barcode) {
        return barcode.indexOf(':') != -1;
    }

    private static boolean isInventoryId(String barcode) {
        return barcode.toLowerCase().startsWith("i");
    }

    public static boolean isBranchId(String barcode) {
        return barcode.toLowerCase().startsWith("b");
    }

}
