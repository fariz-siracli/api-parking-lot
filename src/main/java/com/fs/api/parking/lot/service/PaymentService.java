package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.dao.model.PaymentEntity;
import com.fs.api.parking.lot.model.PaymentRequestDto;
import com.fs.api.parking.lot.model.PaymentResponseDto;

public interface PaymentService {

    PaymentEntity findPaymentByEvent(ParkingEventEntity parkingEventEntity);

    PaymentResponseDto makePayment(PaymentRequestDto requestDto);
}
