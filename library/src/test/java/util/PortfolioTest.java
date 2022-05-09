package util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PortfolioTest {
    private Portfolio portfolio;

    @Before
    public void setup() {
        portfolio = new Portfolio();
    }

    @Test
    public void countIsZeroWhenCreated() {
        assertThat(portfolio.uniqueSymbolCount(), equalTo(0));
    }

    @Test
    public void countIncreasesAfterPurchase() {
        portfolio.purchase("AAPL", 10);

        assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
    }

    @Test
    public void countIncreasesWithEachPurchaseOfDifferentSymbol() {
        portfolio.purchase("AAPL", 10);
        portfolio.purchase("IBM", 42);

        assertThat(portfolio.uniqueSymbolCount(), equalTo(2));
    }

    @Test
    public void countDoesNotIncreaseWhenSameSymbolPurchased() {
        portfolio.purchase("AAPL", 10);
        portfolio.purchase("AAPL", 42);

        assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
    }


    @Test
    public void sharesForSymbolNotPurchasedIsZero() {
        assertThat(portfolio.shares("AAPL"), equalTo(0));
    }

    @Test
    public void returnsSharesPurchased() {
        portfolio.purchase("IBM", 100);

        assertThat(portfolio.shares("IBM"), equalTo(100));
    }

    @Test
    public void returnsSharesForAppropriateSymbol() {
        portfolio.purchase("IBM", 100);
        portfolio.purchase("AAPL", 42);

        assertThat(portfolio.shares("IBM"), equalTo(100));
    }

    @Test
    public void sumsSharesForSameSymbolPurchase() {
        portfolio.purchase("IBM", 100);
        portfolio.purchase("IBM", 42);

        assertThat(portfolio.shares("IBM"), equalTo(142));
    }

    @Test(expected=InvalidPurchaseException.class)
    public void throwsWhenPurchasingNonpositiveShares() {
        portfolio.purchase("IBM", 0);
    }

    @Test
    public void isEmptyWhenCreated() {
        assertThat(portfolio.isEmpty(), equalTo(true));
    }

    @Test
    public void isNotEmptyAfterPurchase() {
        portfolio.purchase("IBM", 20);
        assertThat(portfolio.isEmpty(), equalTo(false));
    }

    @Test
    public void diminishesSharesAfterSell() {
        portfolio.purchase("AAPL", 100);

        portfolio.sell("AAPL", 40);

        assertThat(portfolio.shares("AAPL"), equalTo(60));
    }

    @Test
    public void reducesCountWhenAllSharesOfSymbolSold() {
        portfolio.purchase("AAPL", 100);

        portfolio.sell("AAPL", 100);

        assertThat(portfolio.uniqueSymbolCount(), equalTo(0));
    }
}
