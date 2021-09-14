package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.model.GateDto;
import com.fs.api.parking.lot.model.ParkingEventDto;
import com.fs.api.parking.lot.model.VehicleDto;

public interface EventService {
    ParkingEventDto vehicleEntranceService(VehicleDto vehicle, GateDto gateEntity);

    void vehicleExitService();
}
