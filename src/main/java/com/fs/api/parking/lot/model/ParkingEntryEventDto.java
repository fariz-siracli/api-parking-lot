package com.fs.api.parking.lot.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("DTO class to response Parking Event")
public class ParkingEntryEventDto {

    private String ticketNumber;

    private String entryTime;

    private VehicleDto vehicleDto;

    private String pricePerMinute;

    private SlotInfoDto slotInfoDto;
}
