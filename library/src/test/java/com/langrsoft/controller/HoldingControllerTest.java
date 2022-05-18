package com.langrsoft.controller;

import com.langrsoft.domain.Branch;
import com.langrsoft.domain.HoldingAlreadyCheckedOutException;
import com.langrsoft.domain.HoldingBuilder;
import com.langrsoft.service.library.HoldingService;
import com.langrsoft.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testutil.ControllerTestUtil.postAsJson;
import static testutil.ControllerTestUtil.resultContent;

@ExtendWith(MockitoExtension.class)
class HoldingControllerTest {
    MockMvc mockMvc;

    @Mock
    HoldingService holdingService;
    @InjectMocks
    HoldingController holdingController;

    static final Date TODAY = new Date();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(holdingController).build();
    }

    @Nested
    class PostHolding {
        @Test
        void addBookHolding() throws Exception {
            var request = new AddHoldingRequest("sourceId", "b1");

            mockMvc.perform(postAsJson("/holdings", request))
                    .andExpect(status().isOk());

            verify(holdingService).add("sourceId", "b1");
        }
    }

    @Nested
    class PostCheckout {
        @Test
        void checksOutFromService() throws Exception {
            var checkoutDate = DateUtil.create(2023, 2, 17);
            var checkoutRequest = new CheckoutRequest("p1", "QA123:42", checkoutDate);

            mockMvc.perform(postAsJson("/holdings/checkout", checkoutRequest))
                    .andExpect(status().isOk());

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

    @Nested
    class GetHoldingByBarcode {
        @Test
        void returnsResponseFromService() throws Exception {
            var holding = new HoldingBuilder().build();
            holding.getMaterial().setAuthor("Heller");
            when(holdingService.find("QA123:1")).thenReturn(holding);

            var result = mockMvc.perform(get("/holdings/QA123:1"))
                    .andExpect(status().isOk())
                    .andReturn();

            var retrieved = resultContent(result, HoldingResponse.class);
            assertThat(retrieved.getBarcode(), equalTo(holding.getBarcode()));
            assertThat(retrieved.getAuthor(), equalTo("Heller"));
        }
    }

    @Nested
    class RetrieveHoldingsByBranchScanCode {
        @Test
        void returnsListOfHoldingResponsesMatchingBranch() throws Exception {
            var branch = new Branch("b1");
            var holding1 = new HoldingBuilder().author("Heller").branch(branch).build();
            var holding2 = new HoldingBuilder().author("Kafka").branch(branch).build();
            when(holdingService.findByBranch("b1"))
                    .thenReturn(List.of(holding1, holding2));

            var result = mockMvc.perform(get("/holdings?branchScanCode=b1"))
                    .andExpect(status().isOk())
                    .andReturn();

            var retrieved = resultContent(result, HoldingResponse[].class);
            var authors = Arrays.stream(retrieved)
                    .map(HoldingResponse::getAuthor).collect(Collectors.toList());
            assertThat(authors, hasItems("Heller", "Kafka"));
        }
    }
}