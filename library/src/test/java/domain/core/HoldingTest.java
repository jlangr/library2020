package domain.core;

import com.loc.material.api.Material;
import com.loc.material.api.MaterialType;
import org.junit.Before;
import org.junit.Test;
import testutil.EqualityTester;
import util.DateUtil;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/*
This test class is a mess. Some of the opportunities for cleanup:

 - AAA used but no visual separation
 - seeming use of AAA but it's not really
 - unnecessary code (null checks? try/catch?)
 - constant names that obscure relevant information
 - can data be created in the test?
 - poor / inconsistent test names
 - comments in tests (are they even true)?
 - multiple behaviors/asserts per test
 - code in the wrong place / opportunities for reuse of existing code
 - dead code
 */

public class HoldingTest {
    static final Material THE_TRIAL = new Material("10", "", "10", "", "");
    static final Material DR_STRANGELOVE = new Material("12", "", "11", "", MaterialType.DVD, "");
    static final Material THE_REVENANT = new Material("12", "", "11", "", MaterialType.NewReleaseDVD, "");
    Holding holding;
    static final Date TODAY = new Date();
    static final Date TOMORROW = addDays(TODAY, 1);
    static final int COPY_NUMBER_1 = 1;
    Branch eastBranch = new Branch("East");
    Branch westBranch = new Branch("West");

    @Before
    public void setUp() {
        holding = new Holding(THE_TRIAL, eastBranch, COPY_NUMBER_1);
    }

    @Test
    public void branchDefaultsToCheckedOutWhenCreated() {
        Holding holding = new Holding(THE_TRIAL);
        assertThat(holding, not(nullValue()));
        assertThat(holding.getBranch(), equalTo(Branch.CHECKED_OUT));
    }

    @Test
    public void copyNumberDefaultsTo1WhenCreated() {
        Holding holding = new Holding(THE_TRIAL, eastBranch);
        assertThat(holding.getCopyNumber(), equalTo(1));
    }

    @Test
    public void changesBranchOnTransfer() {
        holding.transfer(westBranch);
        assertThat(holding.getBranch(), equalTo(westBranch));
    }

    @Test
    public void checkout_setsCheckedOutDate() {
        holding.checkOut(TODAY);

        assertThat(holding.dateCheckedOut(), equalTo(TODAY));
    }

    @Test
    public void checkout_setsDueDateToSomeFutureDate() {
        holding.checkOut(TODAY);

        assertThat(holding.dateCheckedOut(), equalTo(TODAY));
    }

    @Test
    public void checkout_removesHoldingFromBranch() {
        holding.checkOut(TODAY);

        assertThat(holding.getBranch(), equalTo(Branch.CHECKED_OUT));
    }
    @Test
    public void checkout_setsHoldingToAvailable() {
        holding.checkOut(TODAY);

        assertFalse(holding.isAvailable());
    }

    @Test
    public void checkin_updatesCheckedInDate() {
        holding.checkOut(TODAY);

        holding.checkIn(TOMORROW, eastBranch);

        assertThat(holding.dateLastCheckedIn(), equalTo(TOMORROW));
    }

    @Test
    public void checkin_setsHoldingToAvailable() {
        holding.checkOut(TODAY);

        holding.checkIn(TOMORROW, eastBranch);

        assertTrue(holding.isAvailable());
    }

    @Test
    public void checkin_setsBranchReturnedAt() {
        holding.checkOut(TODAY);

        holding.checkIn(TOMORROW, eastBranch);

        assertThat(holding.getBranch(), equalTo(eastBranch));
    }

    @Test
    public void returnDateForStandardBook() {
        holding.checkOut(TODAY);
        Date dateDue = holding.dateDue();
        assertDateEquals(addDays(TODAY, MaterialType.Book.getCheckoutPeriod()), dateDue);
    }

    @Test
    public void dateDueNullWhenCheckedOutIsNull() {
        assertThat(holding.dateDue(), equalTo(null));
    }

    @Test
    public void daysLateIsZeroWhenDateDueIsNull() {
        assertThat(holding.daysLate(), equalTo(0));
    }

    @Test
    public void checkout_period_for_DVD() {
        checkOutToday(DR_STRANGELOVE, eastBranch);

        var dueDate = holding.dateDue();

        assertDateEquals(addDays(TODAY, MaterialType.DVD.getCheckoutPeriod()), dueDate);
    }

    @Test
    public void checkout_period_for_new_release() {
        checkOutToday(THE_REVENANT, eastBranch);

        var expected = addDays(TODAY, MaterialType.NewReleaseDVD.getCheckoutPeriod());

        assertDateEquals(expected, holding.dateDue());
    }

    @Test
    public void answersDaysLateOfZeroWhenReturnedSameDay() {
        checkOutToday(THE_TRIAL, eastBranch);
        int daysLate = holding.checkIn(TODAY, eastBranch);
        assertThat(daysLate, equalTo(0));
    }

    @Test
    public void answersDaysLateOfZeroWhenReturnedOnDateDue() {
        checkOutToday(THE_TRIAL, eastBranch);
        int daysLate = holding.checkIn(holding.dateDue(), eastBranch);
        assertThat(daysLate, equalTo(0));
    }

    @Test
    public void answersDaysLateWhenReturnedAfterDueDate() {
        try {
            checkOutToday(THE_TRIAL, eastBranch);
            var lateDate = DateUtil.addDays(holding.dateDue(), 3);

            int days = holding.checkIn(lateDate, eastBranch);

            assertThat(days, equalTo(3));
        } catch (RuntimeException notReallyExpected) {
            fail();
        }
    }

    @Test
    public void isLateWhenCheckedInPastDueDate() {
        checkOutToday(THE_TRIAL, eastBranch);
        var lateDate = DateUtil.addDays(holding.dateDue(), 3);

        holding.checkIn(lateDate, eastBranch);

        assertThat(holding.isLate(), equalTo(true));
    }

    @Test
    public void isLateAfterDateDueOfYearEnd() {
        holding = new Holding(THE_TRIAL, eastBranch);
        holding.checkOut(DateUtil.create(2022, 11, 31));
        var lateDate = DateUtil.addDays(holding.dateDue(), 3);

        holding.checkIn(lateDate, eastBranch);

        assertThat(holding.isLate(), equalTo(true));
    }

    @Test
    public void isNotLateWhenCheckedInUpToDueDate() {
        checkOutToday(THE_TRIAL, eastBranch);

        holding.checkIn(holding.dateDue(), eastBranch);

        assertThat(holding.isLate(), equalTo(false));
    }

    private void checkOutToday(Material material, Branch branch) {
        holding = new Holding(material, branch);
        holding.checkOut(TODAY);
    }

    public static Date addDays(Date date, int days) {
        return new Date(date.getTime() + days * 60L * 1000 * 60 * 24);
    }

    public static void assertDateEquals(Date expectedDate, Date actualDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expectedDate);
        int expectedYear = calendar.get(Calendar.YEAR);
        int expectedDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(actualDate);
        assertThat(calendar.get(Calendar.YEAR), equalTo(expectedYear));
        assertThat(calendar.get(Calendar.DAY_OF_YEAR), equalTo(expectedDayOfYear));
    }

    @Test
    public void equality() {
        Holding holding1 = new Holding(THE_TRIAL, eastBranch, 1);
        Holding holding1Copy1 = new Holding(THE_TRIAL, westBranch, 1); // diff loc but same copy
        Holding holding1Copy2 = new Holding(THE_TRIAL, Branch.CHECKED_OUT, 1);
        Holding holding2 = new Holding(THE_TRIAL, eastBranch, 2); // 2nd copy
        Holding holding1Subtype = new Holding(THE_TRIAL, eastBranch,
                1) {
        };

        new EqualityTester(holding1, holding1Copy1, holding1Copy2, holding2,
                holding1Subtype).verify();
    }
}