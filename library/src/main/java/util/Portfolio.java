package util;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    static final String MSG_INVALID_SHARES = "Shares must be positive";
    private Map<String,Integer> holdings = new HashMap<>();

    public int uniqueSymbolCount() {
        return holdings.size();
    }

    public void buy(String symbol, int shares) {
        throwOnInvalidShares(shares);
        holdings.put(symbol, shares(symbol) + shares);
    }

    private void throwOnInvalidShares(int shares) {
        if (shares == 0) throw new InvalidBuyException(MSG_INVALID_SHARES);
    }

    public int shares(String symbol) {
        if (!holdings.containsKey(symbol)) return 0;

        return holdings.get(symbol);
    }
}
