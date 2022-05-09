package util;

//import org.junit.Test;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class APortfolioTest {
    private Portfolio portfolio = new Portfolio();

    @Test
    void answersUniqueSymbolCountAsZero_WhenNoPurchasesMode() {
        assertThat(portfolio.uniqueSymbolCount(), equalTo(0));
    }

    @Test
    public void answersUniqueSymbolCountAsToOne_AfterPurchaseMade() {
        portfolio.buy("ZBRA");

        assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
    }

    @Test
    public void incrementsUniqueSymbolCount_WithEachNewSymbolPurchased() {
        portfolio.buy("ZBRA");
        portfolio.buy("AAPL");

        assertThat(portfolio.uniqueSymbolCount(), equalTo(2));
    }

    @Test
    public void doesNotIncrementUniqueSymbolCount_WhenSameSymbolPurchased() {
        portfolio.buy("ZBRA");
        portfolio.buy("ZBRA");

        assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
    }
}
