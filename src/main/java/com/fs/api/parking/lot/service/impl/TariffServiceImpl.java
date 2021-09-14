package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.dao.TariffRepository;
import com.fs.api.parking.lot.dao.model.Tariff;
import com.fs.api.parking.lot.exception.DPException;
import com.fs.api.parking.lot.logger.DPLogger;
import com.fs.api.parking.lot.model.VehicleDto;
import com.fs.api.parking.lot.service.TariffService;
import org.springframework.stereotype.Service;

@Service
public class TariffServiceImpl implements TariffService {

    private static final DPLogger logger = DPLogger.getLogger(TariffServiceImpl.class);

    private final TariffRepository tariffRepository;

    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Tariff findTariffByVehicle(VehicleDto vehicleDto) {
        //return Mock Tariff according to vehicle params
        var opt = tariffRepository.findById(1L);
        if (opt.isEmpty()) {
            throw new DPException("exception.parking-lot.tariff-not-found", "Tariff not found");
        }
        return opt.get();
    }
}
