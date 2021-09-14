package com.fs.api.parking.lot.rest;

import com.fs.api.parking.lot.model.PaymentRequestDto;
import com.fs.api.parking.lot.model.PaymentResponseDto;
import com.fs.api.parking.lot.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api.root.url}" + "/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/execute-payment")
    public PaymentResponseDto executePayment(@RequestBody PaymentRequestDto requestDto) {
        return paymentService.makePayment(requestDto);
    }
}
