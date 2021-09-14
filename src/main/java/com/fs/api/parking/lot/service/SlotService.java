package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.Gate;
import com.fs.api.parking.lot.dao.model.Slot;
import com.fs.api.parking.lot.dao.model.Vehicle;

public interface SlotService {
    Slot findSlotForVehicle(Vehicle vehicle, Gate gate);

    void freeSlot(Slot slot);
}
