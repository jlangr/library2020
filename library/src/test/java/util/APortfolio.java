package util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class APortfolio {
    public static final String ZEBRA = "ZBRA";
    public static final String APPLE = "AAPL";
    private Portfolio portfolio = new Portfolio();

    @Nested
    class ErrorsArise {
        @Test
        void whenBuyingNonPositiveShares() {
            var thrown = assertThrows(InvalidBuyException.class,
                    () -> portfolio.buy(ZEBRA, 0));
            assertThat(thrown.getMessage(), equalTo(Portfolio.MSG_INVALID_SHARES));
        }
    }

    @Nested
    class IsEmpty {
        @Test
        void isTrueWhenNoPurchasesMade() {
            assertThat(portfolio.isEmpty(), equalTo(true));
        }

        @Test
        void isFalseWhenPurchasesMade() {
            portfolio.buy(ZEBRA, 42);
            assertThat(portfolio.isEmpty(), equalTo(false));
        }
    }

    @Nested
    class UniqueSymbolCount {
        @Test
        void isZeroWhenNoPurchasesMode() {
            assertThat(portfolio.uniqueSymbolCount(), equalTo(0));
        }

        @Test
        void isOneAfterPurchaseMade() {
            portfolio.buy(ZEBRA, 1);

            assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
        }

        @Test
        void incrementsWithEachNewSymbolPurchase() {
            portfolio.buy(ZEBRA, 1);
            portfolio.buy(APPLE, 1);

            assertThat(portfolio.uniqueSymbolCount(), equalTo(2));
        }

        @Test
        public void doesNotIncrementOnSameSymbolPurchase() {
            portfolio.buy(ZEBRA, 1);
            portfolio.buy(ZEBRA, 1);

            assertThat(portfolio.uniqueSymbolCount(), equalTo(1));
        }
    }

    @Nested
    class SharesOfSymbol {
        @Test
        void isZeroForSymbolNotPurchased() {
            assertThat(portfolio.shares(ZEBRA), equalTo(0));
        }

        @Test
        void representsSharesPurchased() {
            portfolio.buy(ZEBRA, 42);

            assertThat(portfolio.shares(ZEBRA), equalTo(42));
        }

        @Test
        void areKeptDistinctBySymbol() {
            portfolio.buy(ZEBRA, 42);
            portfolio.buy(APPLE, 100);

            assertThat(portfolio.shares(ZEBRA), equalTo(42));
        }

        @Test
        void areAccumulatedBySymbol() {
            portfolio.buy(ZEBRA, 50);
            portfolio.buy(ZEBRA, 100);

            assertThat(portfolio.shares(ZEBRA), equalTo(150));
        }
    }
}
