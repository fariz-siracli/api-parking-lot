package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.model.ExitRequest;
import com.fs.api.parking.lot.model.GateDto;
import com.fs.api.parking.lot.model.ParkingEntryEventDto;
import com.fs.api.parking.lot.model.ParkingExitEventDto;
import com.fs.api.parking.lot.model.VehicleDto;

public interface EventService {
    ParkingEntryEventDto vehicleEntrance(VehicleDto vehicle, GateDto gateEntity);

    ParkingExitEventDto vehicleExit(ExitRequest request);
}
