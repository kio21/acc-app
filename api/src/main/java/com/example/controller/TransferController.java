package com.example.controller;

import com.example.request.MoneyTransferRequest;
import com.example.response.TransferResponse;
import com.example.service.MoneyTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/transfer")
public class TransferController extends PrivateController {
    private final MoneyTransferService moneyTransferService;

    public TransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<TransferResponse> moneyTransfer(
        @RequestBody @Valid MoneyTransferRequest request) {
        return auth(user -> new TransferResponse()
            .status(moneyTransferService.transfer(user, request.data()))
            .userFrom(user.id())
            .userTo(request.getUserId())
            .value(request.getValue())
        );
    }
}
