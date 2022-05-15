package com.langrsoft.controller;

import com.langrsoft.service.library.BranchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langrsoft.domain.Branch;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {
    MockMvc mockMvc;

    @Mock
    BranchService branchService;
    @InjectMocks
    BranchController branchController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
    }

    @Test
    public void retrieveAll() throws Exception {
        var branchRequest1 = new BranchRequest(new Branch("b1", "branch A"));
        var branchRequest2 = new BranchRequest(new Branch("b2", "branch B"));
        when(branchService.retrieveAllBranches())
                .thenReturn(asList(branchRequest1, branchRequest2));

        var result = mockMvc.perform(get("/branches"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(resultContent(result, BranchRequest[].class),
                Matchers.arrayContaining(branchRequest1, branchRequest2));
    }

    private <T> T resultContent(MvcResult resultActions, Class<T> valueType)
            throws UnsupportedEncodingException, JsonProcessingException {
        var response = resultActions.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, valueType);
    }

}