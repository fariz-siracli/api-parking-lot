package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.Tariff;
import com.fs.api.parking.lot.model.VehicleDto;

public interface TariffService {

    Tariff findTariffByVehicle(VehicleDto vehicleDto);
}
