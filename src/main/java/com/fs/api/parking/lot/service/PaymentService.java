package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.ParkingEvent;
import com.fs.api.parking.lot.dao.model.Payment;
import com.fs.api.parking.lot.model.PaymentRequestDto;
import com.fs.api.parking.lot.model.PaymentResponseDto;

public interface PaymentService {

    Payment findPaymentByEvent(ParkingEvent parkingEvent);

    PaymentResponseDto makePayment(PaymentRequestDto requestDto);
}
