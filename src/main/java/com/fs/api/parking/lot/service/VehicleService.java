package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.Vehicle;
import com.fs.api.parking.lot.model.VehicleDto;

public interface VehicleService {

    Vehicle save(VehicleDto vehicle);
}
