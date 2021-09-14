package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.dao.PaymentRepository;
import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.dao.model.PaymentEntity;
import com.fs.api.parking.lot.exception.DPException;
import com.fs.api.parking.lot.logger.DPLogger;
import com.fs.api.parking.lot.mapper.PaymentMapper;
import com.fs.api.parking.lot.model.PaymentRequestDto;
import com.fs.api.parking.lot.model.PaymentResponseDto;
import com.fs.api.parking.lot.service.PaymentService;
import com.fs.api.parking.lot.util.EventHelper;
import com.fs.api.parking.lot.util.PaymentHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.fs.api.parking.lot.model.enums.PaymentStatus.SUCCESS;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final DPLogger logger = DPLogger.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final EventHelper eventHelper;
    private final PaymentHelper paymentHelper;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              EventHelper eventHelper, PaymentHelper paymentHelper) {
        this.paymentRepository = paymentRepository;
        this.eventHelper = eventHelper;
        this.paymentHelper = paymentHelper;
    }

    @Override
    public PaymentEntity findPaymentByEvent(ParkingEventEntity parkingEventEntity) {
        logger.info("LogEvent.findPaymentByEvent.start : gate {}", parkingEventEntity.getId());

        var paymentOpt = paymentRepository.findByParkingEventEntity(parkingEventEntity);
        if (paymentOpt.isEmpty()) {
            throw new DPException("exception.parking-lot.payment-not-found", "Requested Payment not found");
        }

        logger.info("LogEvent.findPaymentByEvent.end : gate {}", parkingEventEntity.getId());
        return paymentOpt.get();
    }

    @Override
    public PaymentResponseDto makePayment(PaymentRequestDto requestDto) {
        logger.info("LogEvent.makePayment.start : ticket {}", requestDto.getTicketNumber());

        var event = eventHelper.findEventByTicket(requestDto.getTicketNumber());
        var mockAmount = BigDecimal.valueOf(10.5);
        var paymentResponse = paymentHelper.executePayment(mockAmount);

        if (!paymentResponse.getSuccess()) {
            throw new DPException("exception.parking-lot.payment-not-passed", "Requested Payment not passed");
        }
        var payment = PaymentEntity.builder()
                .parkingEventEntity(event)
                .amount(mockAmount)
                .paymentStatus(SUCCESS)
                .build();

        paymentRepository.save(payment);
        var response = PaymentMapper.INSTANCE.entityToDto(payment);

        logger.info("LogEvent.makePayment.end : ticket {}", requestDto.getTicketNumber());
        return response;
    }
}
