package util;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    static final String MSG_INVALID_SHARES = "Shares must be positive";
    static final String MSG_OVERSELL = "Oversell prohibited";
    private Map<String,Integer> holdings = new HashMap<>();
    private StockLookupService stockService = new NasdaqStockLookupService();

    private PortfolioAuditor auditor;

    public int uniqueSymbolCount() {
        return holdings.size();
    }

    public void buy(String symbol, int shares) {
        throwOnInvalidShares(shares);
        transact(symbol, shares);
    }

    public void sell(String symbol, int shares) {
        auditor.audit(String.format("bought %s", symbol));
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

    public int value() {
        return holdings.keySet().stream()
                .map(symbol -> {
                    try {
                        return stockService.price(symbol) * shares(symbol);
                    }
                    catch (RuntimeException e) {
                        return 0;
                    }
                })
                .reduce(0, (tot, valueForSymbol) -> tot + valueForSymbol);
    }

    public void setStockLookupService(StockLookupService stockService) {
        this.stockService = stockService;
    }
}
