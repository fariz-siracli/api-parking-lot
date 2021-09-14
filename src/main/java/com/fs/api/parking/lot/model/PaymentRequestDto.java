package com.fs.api.parking.lot.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DTO class to request Parking fee payment")
public class PaymentRequestDto {

    @NotNull
    private String ticketNumber;
}
