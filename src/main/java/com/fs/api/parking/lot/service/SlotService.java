package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.GateEntity;
import com.fs.api.parking.lot.dao.model.SlotEntity;
import com.fs.api.parking.lot.dao.model.VehicleEntity;
import com.fs.api.parking.lot.model.GateDto;

public interface SlotService {
    SlotEntity slotAssignService(VehicleEntity vehicleEntity, GateEntity gateEntity);

    void slotFreeService(SlotEntity slotEntity);
}
