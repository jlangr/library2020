package com.langrsoft.controller;

import com.langrsoft.domain.HoldingAlreadyCheckedOutException;
import com.langrsoft.service.library.HoldingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/holdings")
public class HoldingController {
    HoldingService service = new HoldingService();

    @PostMapping
    public String addBookHolding(@RequestBody AddHoldingRequest request) {
        return service.add(request.getSourceId(), request.getBranchScanCode());
    }

    @PostMapping(value = "/checkout")
    public void checkout(@RequestBody CheckoutRequest request, HttpServletResponse response) {
        try {
            service.checkOut(request.getPatronId(), request.getHoldingBarcode(), request.getCheckoutDate());
        } catch (HoldingAlreadyCheckedOutException exception) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
    }

    @PostMapping(value = "/checkin")
    public void checkin(@RequestBody CheckinRequest request) {
        service.checkIn(request.getHoldingBarcode(), request.getCheckinDate(), request.getBranchScanCode());
    }

    @GetMapping
    public List<HoldingResponse> retrieveHoldingsByQuery(
            @RequestParam(value = "branchScanCode") String scanCode) {
        return HoldingResponse.create(service.findByBranch(scanCode));
    }

    @GetMapping(value = "/{holdingBarcode}")
    public HoldingResponse retrieve(@PathVariable("holdingBarcode") String holdingBarcode) {
        return new HoldingResponse(service.find(holdingBarcode));
    }
}