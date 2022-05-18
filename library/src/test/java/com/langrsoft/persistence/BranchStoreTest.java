package com.langrsoft.persistence;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static testutil.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

class BranchStoreTest {
    private BranchStore store;
    private static final Branch EAST_BRANCH = new Branch("East");

    @BeforeEach
    void initialize() {
        BranchStore.deleteAll();
        store = new BranchStore();
    }

    @Test
    void assignsIdToBranch() {
        var branch = new Branch("name");

        store.save(branch);

        assertThat(branch.getScanCode(), startsWith("b"));
    }

    @Test
    void assignedIdIsUnique() {
        var branchA = new Branch("a");
        store.save(branchA);
        var branchB = new Branch("b");

        store.save(branchB);

        assertThat(branchA.getScanCode(), not(equalTo(branchB.getScanCode())));
    }

    @Test
    void doesNotChangeIdIfAlreadyAssigned() {
        var branch = new Branch("b1964", "");

        store.save(branch);

        assertThat(branch.getScanCode(), equalTo("b1964"));
    }

    @Test
    void returnsSavedBranches() {
        store.save(new Branch("name"));

        var retrieved = store.findByName("name");

        assertThat(retrieved.getName(), equalTo("name"));
    }

    @Test
    void returnsNewInstanceOfPersistedBranch() {
        var branch = new Branch("name");
        store.save(branch);
        store = new BranchStore();

        var retrieved = store.findByName("name");

        assertThat(branch, not(sameInstance(retrieved)));
    }

    @Test
    void returnsListOfAllBranches() {
        var branch = new Branch("b123", "");
        store.save(branch);

        var branches = store.getAll();

        assertThat(branches, hasExactlyItemsInAnyOrder(branch));
    }

    @Test
    void findsBranchByScanCode() {
        store.save(EAST_BRANCH);

        var retrieved = store.findByScanCode(EAST_BRANCH.getScanCode());

        assertThat(retrieved, is(EAST_BRANCH));
    }

    @Test
    void findsCheckedOutBranch() {
        assertThat(store.findByScanCode(Branch.CHECKED_OUT.getScanCode()),
                is(sameInstance(Branch.CHECKED_OUT)));
    }

    @Test
    void findsBranchByScanCodeReturnsNullWhenNotFound() {
        assertThat(store.findByScanCode(""), nullValue());
    }
}
