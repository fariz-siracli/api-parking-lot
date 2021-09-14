package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.DPLogger;
import com.fs.api.parking.lot.dao.FloorRepository;
import com.fs.api.parking.lot.dao.model.FloorEntity;
import com.fs.api.parking.lot.exception.DPException;
import com.fs.api.parking.lot.service.FloorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fs.api.parking.lot.model.enums.FloorState.AVAILABLE;

@Service
public class FloorServiceImpl implements FloorService {

    private static final DPLogger logger = DPLogger.getLogger(FloorServiceImpl.class);

    private final FloorRepository floorRepository;

    public FloorServiceImpl(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Override
    public List<FloorEntity> findAvailableByHeightAndWeightFloors(int height, int weight, FloorEntity floorEntity) {
        logger.info("LogEvent.findAvailableByHeightAndWeightFloors.start : floor {}", floorEntity.getId());

        List<FloorEntity> floorList = floorRepository
                .findByStateAndHeightLessThan(AVAILABLE.name(), height, floorEntity.getId());
        if (floorList.size() == 0) {
            throw new DPException("exception.parking-lot.no-floor-available", "There is no floor for requested params");
        }

        var available = floorList.stream()
                .filter(f -> {
                    int floorCurrentWeight = f.getSlotSet()
                            .stream()
                            .filter(s -> Objects.nonNull(s.getCurrentVehicleEntity()))
                            .mapToInt(s -> s.getCurrentVehicleEntity().getWeight())
                            .sum();
                    return (floorCurrentWeight + weight) < f.getWeight();
                }).collect(Collectors.toList());
        logger.info("LogEvent.findAvailableByHeightAndWeightFloors.start : floor {}, count is {}",
                floorEntity.getId(), available.size());
        return available;
    }
}
