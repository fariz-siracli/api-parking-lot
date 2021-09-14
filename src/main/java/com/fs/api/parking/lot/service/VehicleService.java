package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.VehicleEntity;
import com.fs.api.parking.lot.model.VehicleDto;

public interface VehicleService {

    VehicleEntity saveVehicle(VehicleDto vehicle);
}
