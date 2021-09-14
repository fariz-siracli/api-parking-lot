package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.dao.model.Floor;
import com.fs.api.parking.lot.logger.DPLogger;
import com.fs.api.parking.lot.dao.FloorRepository;
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
    public List<Floor> findAvailableByHeightAndWeightFloors(int height, int weight, Floor floor) {
        logger.info("LogEvent.findAvailableByHeightAndWeightFloors.start : floor {}", floor.getId());

        List<Floor> floorList = floorRepository
                .findByStateAndHeightLessThan(AVAILABLE.name(), height, floor.getId());
        if (floorList.size() == 0) {
            throw new DPException("exception.parking-lot.no-floor-available", "There is no floor for requested params");
        }

        var available = floorList.stream()
                .filter(f -> {
                    int floorCurrentWeight = f.getSlotSet()
                            .stream()
                            .filter(s -> Objects.nonNull(s.getCurrentVehicle()))
                            .mapToInt(s -> s.getCurrentVehicle().getWeight())
                            .sum();
                    return (floorCurrentWeight + weight) < f.getWeight();
                }).collect(Collectors.toList());
        logger.info("LogEvent.findAvailableByHeightAndWeightFloors.start : floor {}, count is {}",
                floor.getId(), available.size());
        return available;
    }
}
