package util;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    static final String MSG_INVALID_SHARES = "Shares must be positive";
    static final String MSG_OVERSELL = "Oversell prohibited";
    private Map<String,Integer> holdings = new HashMap<>();
    private StockLookupService stockService = new NasdaqStockLookupService();

    public int uniqueSymbolCount() {
        return holdings.size();
    }

    public void buy(String symbol, int shares) {
        throwOnInvalidShares(shares);
        transact(symbol, shares);
    }

    public void sell(String symbol, int shares) {
        throwOnOversell(symbol, shares);
        throwOnInvalidShares(shares);
        transact(symbol, -shares);
        removeSymbolOnSelloff(symbol);
    }

    private void removeSymbolOnSelloff(String symbol) {
        if (shares(symbol) == 0)
            holdings.remove(symbol);
    }

    private void throwOnOversell(String symbol, int shares) {
        if (shares(symbol) < shares) throw new InvalidTransactionException(MSG_OVERSELL);
    }

    private void transact(String symbol, int shares) {
        holdings.put(symbol, shares(symbol) + shares);
    }

    private void throwOnInvalidShares(int shares) {
        if (shares <= 0) throw new InvalidTransactionException(MSG_INVALID_SHARES);
    }

    public int shares(String symbol) {
        if (!holdings.containsKey(symbol)) return 0;

        return holdings.get(symbol);
    }

    public boolean isEmpty() {
        return holdings.isEmpty();
    }
}
