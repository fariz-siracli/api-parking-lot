package com.fs.api.parking.lot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotInfoDto {
    private String floorNumber;
    private String slotNumber;
}
