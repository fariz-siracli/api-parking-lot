package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.FloorEntity;
import com.fs.api.parking.lot.dao.model.SlotEntity;
import com.fs.api.parking.lot.dao.model.VehicleEntity;

import java.util.List;

public interface SlotSearchService {
    SlotEntity findFirstFreeSlot(List<FloorEntity> floorList, VehicleEntity vehicleEntity);
}
