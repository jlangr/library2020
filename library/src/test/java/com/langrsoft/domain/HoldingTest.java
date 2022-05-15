package com.langrsoft.domain;

import com.langrsoft.external.Material;
import com.langrsoft.external.MaterialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.EqualityTester;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

/*
This test class is a mess. Some opportunities for cleanup:

 - AAA used but no visual separation
 - seeming use of AAA, but it's not really
 - unnecessary code (null checks? try/catch?)
 - constant names that obscure relevant information
 - can data be created in the test?
 - poor / inconsistent test names
 - comments in tests (are they even true)?
 - multiple behaviors/asserts per test
 - code in the wrong place / opportunities for reuse of existing code
 - dead code
 */

class HoldingTest {
    static final Material THE_TRIAL = new Material("10", "", "10", "", "");
    static final Material DR_STRANGELOVE = new Material("12", "", "11", "", MaterialType.DVD, "");
    static final Material THE_REVENANT = new Material("12", "", "11", "", MaterialType.NEW_RELEASE_DVD, "");
    Holding h;
    static final Date TODAY = new Date();
    static final int COPY_NUMBER_1 = 1;
    Branch eastBranch = new Branch("East");
    Branch westBranch = new Branch("West");

    @BeforeEach
    void setUp() {
        h = new Holding(THE_TRIAL, eastBranch, COPY_NUMBER_1);
    }

    @Test
    void branchDefaultsToCheckedOutWhenCreated() {
        var holding = new Holding(THE_TRIAL);
        assertThat(holding, not(nullValue()));
        assertThat(holding.getBranch(), equalTo(Branch.CHECKED_OUT));
    }

    @Test
    void copyNumberDefaultsTo1WhenCreated() {
        var holding = new Holding(THE_TRIAL, eastBranch);
        assertThat(holding.getCopyNumber(), equalTo(1));
    }

    @Test
    void changesBranchOnTransfer() {
        h.transfer(westBranch);
        assertThat(h.getBranch(), equalTo(westBranch));
    }

    @Test
    void ck() {
        h.checkOut(TODAY);
        assertThat(h.dateCheckedOut(), equalTo(TODAY));
        assertThat(h.dateDue().after(TODAY), equalTo(true));
        assertThat(h.getBranch(), equalTo(Branch.CHECKED_OUT));
        assertThat(h.isAvailable(), equalTo(false));

        // try again
        h.checkOut(TODAY);
        Date tomorrow = new Date(TODAY.getTime() + 60L + 60 * 1000 * 24);
        h.checkIn(tomorrow, eastBranch);
//        System.out.println("tomorrow:" + tomorrow);
        assertThat(h.dateLastCheckedIn(), equalTo(tomorrow));
        assertThat(h.isAvailable(), equalTo(true));
        assertThat(h.getBranch(), equalTo(eastBranch));
    }

    @Test
    void returnDateForStandardBook() {
        h.checkOut(TODAY);
        var dateDue = h.dateDue();
        assertDateEquals(addDays(TODAY, MaterialType.BOOK.getCheckoutPeriod()), dateDue);
    }

    @Test
    void dateDueNullWhenCheckedOutIsNull() {
        assertThat(h.dateDue(), equalTo(null));
    }

    @Test
    void daysLateIsZeroWhenDateDueIsNull() {
        assertThat(h.daysLate(), equalTo(0));
    }

    @Test
    void testSomething() {
        // movie
        checkOutToday(DR_STRANGELOVE, eastBranch);
        var expected = addDays(TODAY, MaterialType.DVD.getCheckoutPeriod());
        assertDateEquals(addDays(TODAY, MaterialType.DVD.getCheckoutPeriod()), h.dateDue());

        // childrens movie
        checkOutToday(THE_REVENANT, eastBranch);
        expected = addDays(TODAY, MaterialType.NEW_RELEASE_DVD.getCheckoutPeriod());
        assertDateEquals(expected, h.dateDue());
    }

    @Test
    void answersDaysLateOfZeroWhenReturnedSameDay() {
        checkOutToday(THE_TRIAL, eastBranch);
        var daysLate = h.checkIn(TODAY, eastBranch);
        assertThat(daysLate, equalTo(0));
    }

    @Test
    void answersDaysLateOfZeroWhenReturnedOnDateDue() {
        checkOutToday(THE_TRIAL, eastBranch);
        int daysLate = h.checkIn(h.dateDue(), eastBranch);
        assertThat(daysLate, equalTo(0));
    }

    @Test
    void answersDaysLateWhenReturnedAfterDueDate() {
        try {
            checkOutToday(THE_TRIAL, eastBranch);
            Date date = addDays(h.dateDue(), 3);
            int days = h.checkIn(date, eastBranch);
            assertThat(days, equalTo(3));
        } catch (RuntimeException notReallyExpected) {
            fail();
        }
    }

    private void checkOutToday(Material material, Branch branch) {
        h = new Holding(material, branch);
        h.checkOut(TODAY);
    }

    static Date addDays(Date date, int days) {
        return new Date(date.getTime() + days * 60L * 1000 * 60 * 24);
    }

    static void assertDateEquals(Date expectedDate, Date actualDate) {
        var calendar = Calendar.getInstance();
        calendar.setTime(expectedDate);
        var expectedYear = calendar.get(Calendar.YEAR);
        var expectedDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(actualDate);
        assertThat(calendar.get(Calendar.YEAR), equalTo(expectedYear));
        assertThat(calendar.get(Calendar.DAY_OF_YEAR), equalTo(expectedDayOfYear));
    }

    @Test
    void equality() {
        var holding1 = new Holding(THE_TRIAL, eastBranch, 1);
        var holding1Copy1 = new Holding(THE_TRIAL, westBranch, 1); // diff loc but same copy
        var holding1Copy2 = new Holding(THE_TRIAL, Branch.CHECKED_OUT, 1);
        var holding2 = new Holding(THE_TRIAL, eastBranch, 2); // 2nd copy
        var holding1Subtype = new Holding(THE_TRIAL, eastBranch,
                1) {
        };

        new EqualityTester(holding1, holding1Copy1, holding1Copy2, holding2,
                holding1Subtype).verify();
    }
}
