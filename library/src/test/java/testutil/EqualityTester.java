package testutil;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class EqualityTester {
    private Object object1;
    private Object object1Copy1;
    private Object object1Copy2;
    private Object object2;
    private Object object1Subtype;

    public EqualityTester(Object object1, Object object1Copy1,
                          Object object1Copy2, Object object2, Object object1Subtype) {
        this.object1 = object1;
        this.object1Copy1 = object1Copy1;
        this.object1Copy2 = object1Copy2;
        this.object2 = object2;
        this.object1Subtype = object1Subtype;
    }

    public void verify() {
        assertThat(object1.equals(object1Copy1), equalTo(true));
        assertThat(object1.equals(object2), equalTo(false));
        assertThat(object1.equals(object1Subtype), equalTo(false));

        assertNullComparison();
        assertConsistency();
        assertTransitivity();
        assertReflexivity();
        assertSymmetry();
    }

    private void assertNullComparison() {
        assertThat(object1.equals(null), equalTo(false)); // TODO hamcrest
    }

    private void assertConsistency() {
        assertThat(object1.equals(object1Copy1), equalTo(true));
        assertThat(object1.equals(object2), equalTo(false));
    }

    private void assertTransitivity() {
        assertThat(object1Copy1.equals(object1Copy2), equalTo(true));
        assertThat(object1.equals(object1Copy2), equalTo(true));
    }

    private void assertSymmetry() {
        assertThat(object1.equals(object1Copy1), equalTo(true));
        assertThat(object1Copy1.equals(object1), equalTo(true));
    }

    private void assertReflexivity() {
        assertThat(object1.equals(object1), equalTo(true));
    }
}
