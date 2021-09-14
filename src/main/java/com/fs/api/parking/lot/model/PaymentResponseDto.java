package com.fs.api.parking.lot.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DTO class to response Parking fee payment")
public class PaymentResponseDto {
    private String amount;
    private String paymentTime;
    private String durationInMinutes;
}
