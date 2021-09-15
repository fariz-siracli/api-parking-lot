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
public class VehicleDto {

    private String plateNumber;

    @NotNull
    private Integer weight;

    @NotNull
    private Integer height;

}
