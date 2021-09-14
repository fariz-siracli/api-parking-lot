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
@ApiModel("DTO class to response Parking Event")
public class ParkingExitEventDto {

    private String ticketNumber;

    private String exitTime;
}
