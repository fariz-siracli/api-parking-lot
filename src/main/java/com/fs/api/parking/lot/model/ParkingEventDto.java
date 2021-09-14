package com.fs.api.parking.lot.model;

import com.fs.api.parking.lot.dao.model.VehicleEntity;
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
public class ParkingEventDto {

    private String ticketNumber;

    private LocalDateTime entryTime;

    private String exitTime;

    private VehicleEntity vehicleEntity;
}
