package com.langrsoft.controller;

import com.langrsoft.domain.Branch;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class BranchRequest {
    private String name;
    private String id;

    public static List<BranchRequest> create(List<Branch> branches) {
        return branches.stream()
                .map(BranchRequest::new)
                .collect(toList());
    }

    public BranchRequest() {
    }

    public BranchRequest(Branch branch) {
        this.name = branch.getName();
        this.id = branch.getScanCode();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((BranchRequest)o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("BranchRequest{name='%s', id='%s'}", name, id);
    }
}