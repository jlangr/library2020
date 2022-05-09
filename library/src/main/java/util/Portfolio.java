package util;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<String,Integer> holdings = new HashMap<>();

    public int uniqueSymbolCount() {
        return holdings.size();
    }

    public void purchase(String symbol, int shares) {
        if (shares <= 0) throw new InvalidPurchaseException();

        holdings.put(symbol, shares(symbol) + shares);
    }

    public int shares(String symbol) {
        if (!holdings.containsKey(symbol)) return 0;
        return holdings.get(symbol);
    }

    public boolean isEmpty() {
        return 0 == uniqueSymbolCount();
    }

    public void sell(String symbol, int shares) {
        holdings.put(symbol, shares(symbol) - shares);

        if (shares(symbol) == 0)
            holdings.remove(symbol);
    }

}
