package util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class APortfolio {
    private Portfolio portfolio = new Portfolio();

    @Nested
    class UniqueSymbolCount {
        @Test
        void isZeroWhenNoPurchasesMode() {
            assertThat(portfolio.uniqueSymbolCount(), equalTo(0));
        }

        @Test
        void isOneAfterPurchaseMade() {
            portfolio.buy("ZBRA");

            assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
        }

        @Test
        void incrementsWithEachNewSymbolPurchase() {
            portfolio.buy("ZBRA");
            portfolio.buy("AAPL");

            assertThat(portfolio.uniqueSymbolCount(), equalTo(2));
        }

        @Test
        public void doesNotIncrementOnSameSymbolPurchase() {
            portfolio.buy("ZBRA");
            portfolio.buy("ZBRA");

            assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
        }
    }
}
