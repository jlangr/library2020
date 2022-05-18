package testutil;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.List;

import static java.util.Arrays.asList;

public class AllItemsAllFieldsEqual<T> extends TypeSafeDiagnosingMatcher<List<T>> {
    final private List<T> rhsItems;
    private final EqualVerifier equalVerifier = new EqualVerifier();

    public AllItemsAllFieldsEqual(List<T> rhsItems) {
        this.rhsItems = rhsItems;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(asCompareValue(rhsItems));
    }

    @SafeVarargs
    @Factory
    public static <T> Matcher<List<T>> allItemsAllFieldsEqual(T... items) {
        return new AllItemsAllFieldsEqual<>(asList(items));
    }

    @Factory
    public static <T> Matcher<List<T>> allItemsAllFieldsEqual(List<T> items) {
        return new AllItemsAllFieldsEqual<>(items);
    }

    @Override
    protected boolean matchesSafely(List<T> lhsItems, Description mismatchDescription) {
        var result = equalVerifier.areAllFieldsEqualInList(lhsItems, rhsItems);
        if (!result) {
            mismatchDescription.appendText(asCompareValue(lhsItems));
            mismatchDescription.appendText(String.format("\n\nErrors:\n%s", errorMessageString()));
        }
        return result;
    }

    private String errorMessageString() {
        return String.join("\n", equalVerifier.errorMessages());
    }

    private String asCompareValue(List<T> list) {
        return String.format("<[\n%s]>", indexedSummary(list));
    }

    private String indexedSummary(List<T> list) {
        var builder = new StringBuilder();
        for (var i = 0; i < list.size(); i++) {
            if (i != 0)
                builder.append(",\n");
            builder.append(String.format("%d: %s", i, list.get(i)));
        }
        return builder.toString();
    }
}