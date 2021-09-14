package com.fs.api.parking.lot.model;

import com.fs.api.parking.lot.model.enums.GateState;
import com.fs.api.parking.lot.model.enums.GateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GateDto {

    private Long id;

    private String name;

    private GateType type;

    private GateState state;
}
