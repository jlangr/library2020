package com.langrsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langrsoft.domain.HoldingAlreadyCheckedOutException;
import com.langrsoft.service.library.HoldingService;
import com.langrsoft.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HoldingControllerTest {
    MockMvc mockMvc;

    @Mock
    HoldingService holdingService;
    @InjectMocks
    HoldingController holdingController;

    static final Date TODAY = new Date();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(holdingController).build();
    }

    @Nested
    class PostCheckout {
        @Test
        void checksOutFromService() throws Exception {
            var checkoutDate = DateUtil.create(2023, 2, 17);
            var checkoutRequest = new CheckoutRequest("p1", "QA123:42", checkoutDate);

            mockMvc.perform(postAsJson("/holdings/checkout", checkoutRequest))
                    .andExpect(status().isOk())
                    .andReturn();

            verify(holdingService).checkOut("p1", "QA123:42", checkoutDate);
        }

        @Test
        void returnsConflictWhenServiceThrowsAlreadyCheckedOut() throws Exception {
            var checkoutRequest = new CheckoutRequest("", "", new Date());
            doThrow(HoldingAlreadyCheckedOutException.class)
                    .when(holdingService).checkOut(anyString(), anyString(), any(Date.class));

            mockMvc.perform(postAsJson("/holdings/checkout", checkoutRequest))

                    .andExpect(status().isConflict());
        }
    }

    @Nested
    class PostCheckin {
        @Test
        void checksInToService() throws Exception {
            var checkinRequest = new CheckinRequest("QA123:1", TODAY, "b1");

            mockMvc.perform(postAsJson("/holdings/checkin", checkinRequest))

                    .andExpect(status().isOk());

            verify(holdingService).checkIn("QA123:1", TODAY, "b1");
        }
    }

    private MockHttpServletRequestBuilder postAsJson(String url, Object content) {
        try {
            return post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(content));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}