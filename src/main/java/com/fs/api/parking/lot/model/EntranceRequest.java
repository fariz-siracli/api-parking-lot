package com.fs.api.parking.lot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntranceRequest {
    @NotNull
    private VehicleDto vehicleDto;

    @NotNull
    private GateDto entryGateDto;
}
