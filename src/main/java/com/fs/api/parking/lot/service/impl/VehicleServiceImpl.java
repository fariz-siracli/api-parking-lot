package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.DPLogger;
import com.fs.api.parking.lot.dao.VehicleRepository;
import com.fs.api.parking.lot.dao.model.VehicleEntity;
import com.fs.api.parking.lot.model.VehicleDto;
import com.fs.api.parking.lot.service.VehicleService;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final DPLogger logger = DPLogger.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleEntity saveVehicle(VehicleDto vehicle) {
        logger.info("LogEvent.slotAssignService.start");
        VehicleEntity entity = VehicleEntity.builder()
                .height(vehicle.getHeight())
                .weight(vehicle.getWeight())
                .plateNumber(vehicle.getPlateNumber())
                .build();
        entity = vehicleRepository.save(entity);
        logger.info("LogEvent.slotAssignService.start : vehicle saved {}", entity.getId());
        return entity;
    }
}
