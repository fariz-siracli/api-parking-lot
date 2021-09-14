package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.TariffEntity;
import com.fs.api.parking.lot.model.VehicleDto;

public interface TariffService {

    TariffEntity findTariffByVehicle(VehicleDto vehicleDto);
}
