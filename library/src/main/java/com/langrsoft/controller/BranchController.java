package com.langrsoft.controller;

import com.langrsoft.service.library.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    BranchService service;

    public BranchController(@Autowired BranchService service) {
        this.service = service;
    }

    @PostMapping
    public String add(@RequestBody BranchRequest branchRequest) {
        return service.add(branchRequest.getName());
    }

    @GetMapping
    public List<BranchRequest> retrieveAll() {
        return BranchRequest.create(service.allBranches());
    }
}
