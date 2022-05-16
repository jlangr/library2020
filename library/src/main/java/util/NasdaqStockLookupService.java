package util;

public class NasdaqStockLookupService implements StockLookupService {
    @Override
    public int price(String symbol) {
        throw new RuntimeException("The system is down... surprise!");
    }
}
