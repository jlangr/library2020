package testutil;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EqualityTester {
    final private Object object1;
    final private Object object1Copy1;
    final private Object object1Copy2;
    final private Object object2;
    final private Object object1Subtype;

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

        // assert null (object1.equals(null)) is true
        // as long as object1 isn't null itself (and it never should be)
        assertConsistency();
        assertTransitivity();
        assertReflexivity();
        assertSymmetry();
    }

    private void assertConsistency() {
        assertThat(object1.equals(object1Copy1), is(true));
        assertThat(object1.equals(object2), is(false));
    }

    private void assertTransitivity() {
        assertThat(object1Copy1.equals(object1Copy2), is(true));
        assertThat(object1.equals(object1Copy2), is(true));
    }

    private void assertSymmetry() {
        assertThat(object1.equals(object1Copy1), is(true));
        assertThat(object1Copy1.equals(object1), is(true));
    }

    @SuppressWarnings("all")
    private void assertReflexivity() {
        assertThat(object1.equals(object1), is(true));
    }
}
