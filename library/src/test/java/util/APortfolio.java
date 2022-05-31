package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.Asserts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class) JUnit 4
@ExtendWith(MockitoExtension.class) // JUnit 5
class APortfolio {
    public static final String ZEBRA = "ZBRA";
    public static final String APPLE = "AAPL";
    @InjectMocks
    private Portfolio portfolio;
    @Mock
    private StockLookupService stockService;

    @Mock
    private PortfolioAuditor auditor;

    @Test
    void auditsSell() {
        portfolio.buy(ZEBRA, 10);

        portfolio.sell(ZEBRA, 5);

        verify(auditor).audit("bought ZBRA");
    }

    @Nested
    class Value {
        private static final int ZEBRA_CURRENT_PRICE = 3;
        private static final int APPLE_CURRENT_PRICE = 2_000_000;

        @Test
        void isWorthlessWhenNothingPurchase() {
            assertThat(portfolio.value(), equalTo(0));
        }

        @Test
        void multiplesPriceBySharesForSymbol() {
            when(stockService.price(ZEBRA)).thenReturn(ZEBRA_CURRENT_PRICE);

            portfolio.buy(ZEBRA, 10);

            assertThat(portfolio.value(), equalTo(ZEBRA_CURRENT_PRICE * 10));
        }

        @Test
        void addsValueForAllSymbols() {
            when(stockService.price(ZEBRA)).thenReturn(ZEBRA_CURRENT_PRICE);
            when(stockService.price(APPLE)).thenReturn(APPLE_CURRENT_PRICE);

            portfolio.buy(ZEBRA, 10);
            portfolio.buy(APPLE, 20);

            assertThat(portfolio.value(), equalTo(
                    ZEBRA_CURRENT_PRICE * 10 +
                            APPLE_CURRENT_PRICE * 20));
        }

        @Test
        void assumesPriceIsZeroWhenServiceBarfs() {
            when(stockService.price(ZEBRA)).thenReturn(ZEBRA_CURRENT_PRICE);
            when(stockService.price(APPLE)).thenThrow(new RuntimeException());

            portfolio.buy(ZEBRA, 10);
            portfolio.buy(APPLE, 20);

            assertThat(portfolio.value(), equalTo(ZEBRA_CURRENT_PRICE * 10 + APPLE_CURRENT_PRICE * 0));
        }
    }
    @Nested
    class ErrorsArise {
        @ParameterizedTest
        @ValueSource(ints = {0, -1})
        void whenBuyingNonPositiveShares(int shares) {
            var thrown = assertThrows(InvalidTransactionException.class,
                    () -> portfolio.buy(ZEBRA, shares));
            assertThat(thrown.getMessage(), equalTo(Portfolio.MSG_INVALID_SHARES));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -42})
        void whenSellingNonPositiveShares(int shares) {
            var thrown = assertThrows(InvalidTransactionException.class,
                    () -> portfolio.sell(ZEBRA, shares));
            assertThat(thrown.getMessage(), equalTo(Portfolio.MSG_INVALID_SHARES));
        }

        @Test
        void onOversell() {
            var shares = 40;
            portfolio.buy(ZEBRA, shares);

            var thrown = assertThrows(InvalidTransactionException.class,
                    () -> portfolio.sell(ZEBRA, shares + 1));
            assertThat(thrown.getMessage(), equalTo(Portfolio.MSG_OVERSELL));
        }
    }

    @Nested
    class IsEmpty {
        @Test
        void isTrueWhenNoPurchasesMade() {
            assertThat(portfolio.isEmpty(), equalTo(true));
        }

        @Test
        void isTrueOnCompleteSelloff() {
            portfolio.buy(ZEBRA, 42);
            portfolio.sell(ZEBRA, 42);
            assertThat(portfolio.isEmpty(), equalTo(true));
        }
    }

    @Nested
    class UniqueSymbolCount {
        @Test
        void isZeroWhenNothingBought() {
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

        @Test
        void isZeroOnCompleteSelloff() {
            portfolio.buy(ZEBRA, 42);
            portfolio.sell(ZEBRA, 42);
            assertThat(portfolio.uniqueSymbolCount(), equalTo(0));
        }
    }

    @Nested
    class SharesOfSymbol {
        @Test
        void isZeroForSymbolNotPurchased() {
            assertThat(portfolio.shares(ZEBRA), equalTo(0));
        }

        @Test
        void singleBuyReturnsShare() {
            portfolio.buy(ZEBRA, 42);

            assertThat(portfolio.shares(ZEBRA), equalTo(42));
        }


        @Test
        void accumulate() {
            portfolio.buy(ZEBRA, 42);
            portfolio.buy(ZEBRA, 42);

            assertThat(portfolio.shares(ZEBRA), equalTo(84));
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

            assertThat(portfolio.shares(ZEBRA), equalTo(100 + 50));
        }

        @Test
        void areReducedOnSell() {
            portfolio.buy(ZEBRA, 50);
            portfolio.sell(ZEBRA, 20);

            assertThat(portfolio.shares(ZEBRA), equalTo(30));
        }
    }

}
