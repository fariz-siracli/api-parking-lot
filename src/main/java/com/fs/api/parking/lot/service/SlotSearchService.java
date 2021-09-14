package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.Floor;
import com.fs.api.parking.lot.dao.model.Slot;
import com.fs.api.parking.lot.dao.model.Vehicle;

import java.util.List;

public interface SlotSearchService {
    Slot findFirstFreeSlot(List<Floor> floorList, Vehicle vehicle);
}
