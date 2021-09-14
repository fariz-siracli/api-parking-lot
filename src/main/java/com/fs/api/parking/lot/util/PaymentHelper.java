package com.fs.api.parking.lot.util;

import com.fs.api.parking.lot.model.PaymentClientResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentHelper {

    public PaymentClientResponse executePayment(BigDecimal amount) {

        return new PaymentClientResponse(true);
    }
}
