package com.langrsoft.controller;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

class BranchRequestTest {
    @Test
    void createFromListOfBranches() {
        var branch1 = new Branch("b1", "branch1");

        var branch2 = new Branch("b2", "branch2");
        var branches = List.of(branch1, branch2);

        var branchRequests = BranchRequest.create(branches);

        assertThat(branchRequests, contains(new BranchRequest(branch1), new BranchRequest(branch2)));
    }
}
