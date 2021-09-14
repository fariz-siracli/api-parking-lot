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
public class ExitRequest {
    @NotNull
    private String ticket;

    @NotNull
    private GateDto exitGateDto;
}
