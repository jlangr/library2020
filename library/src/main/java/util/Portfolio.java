package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Portfolio {
    private Set<String> symbols = new HashSet<>();

    public int uniqueSymbolCount() {
        return symbols.size();
    }

    public void buy(String symbol) {
        symbols.add(symbol);
    }
}
