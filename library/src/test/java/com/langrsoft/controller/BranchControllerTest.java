package com.langrsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langrsoft.domain.Branch;
import com.langrsoft.service.library.BranchService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    BranchService branchService;
    @InjectMocks
    BranchController branchController;

    BranchRequest branchRequest1;
    BranchRequest branchRequest2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
        branchRequest1 = new BranchRequest(new Branch("b1", "branch A"));
        branchRequest2 = new BranchRequest(new Branch("b2", "branch B"));
    }

    @Test
    void add() throws Exception {
        when(branchService.add(branchRequest1.getName())).thenReturn("branchId1");

        var result = mockMvc.perform(post("/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(branchRequest1)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(contentAsString(result), equalTo("branchId1"));
    }

    @Test
    void retrieveAll() throws Exception {
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
        return new ObjectMapper().readValue(contentAsString(resultActions), valueType);
    }

    private String contentAsString(MvcResult resultActions) throws UnsupportedEncodingException {
        return resultActions.getResponse().getContentAsString();
    }

}