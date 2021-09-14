package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.dao.SlotRepository;
import com.fs.api.parking.lot.dao.model.Floor;
import com.fs.api.parking.lot.dao.model.Gate;
import com.fs.api.parking.lot.dao.model.Slot;
import com.fs.api.parking.lot.dao.model.Vehicle;
import com.fs.api.parking.lot.logger.DPLogger;
import com.fs.api.parking.lot.service.FloorService;
import com.fs.api.parking.lot.service.SlotSearchService;
import com.fs.api.parking.lot.service.SlotService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fs.api.parking.lot.model.enums.FloorState.AVAILABLE;
import static com.fs.api.parking.lot.model.enums.FloorState.FULL;
import static com.fs.api.parking.lot.model.enums.SlotState.FREE;
import static com.fs.api.parking.lot.model.enums.SlotState.IN_USE;

@Service
public class SlotServiceImpl implements SlotService {

    private static final DPLogger logger = DPLogger.getLogger(SlotServiceImpl.class);

    private final SlotSearchService slotSearchService;
    private final FloorService floorService;
    private final SlotRepository slotRepository;

    public SlotServiceImpl(SlotSearchService slotSearchService,
                           FloorService floorService, SlotRepository slotRepository) {
        this.slotSearchService = slotSearchService;

        this.floorService = floorService;
        this.slotRepository = slotRepository;
    }

    @Override
    public Slot findSlotForVehicle(Vehicle vehicle, Gate gate) {
        logger.info("LogEvent.slotAssignService.start : vehicle {}", vehicle.getId());

        List<Floor> availableByHeightFloors = floorService
                .findAvailableByHeightAndWeightFloors(vehicle.getHeight(), vehicle.getHeight(),
                        gate.getRelatedFloor());
        Slot freeSlot = slotSearchService.findFirstFreeSlot(availableByHeightFloors, vehicle);

        freeSlot = updateSlot(vehicle, freeSlot);

        logger.info("LogEvent.slotAssignService.end : vehicle {}", vehicle.getId());
        return freeSlot;
    }

    @Override
    public void freeSlot(Slot occupiedSlot) {
        logger.info("LogEvent.slotFreeService.start : slot {}", occupiedSlot.getId());

        occupiedSlot.setState(FREE);
        occupiedSlot.setCurrentVehicle(null);
        if (occupiedSlot.getRelatedFloor().getState() == FULL) {
            occupiedSlot.getRelatedFloor().setState(AVAILABLE);
        }
        slotRepository.save(occupiedSlot);

        logger.info("LogEvent.slotFreeService.end : slot {}", occupiedSlot.getId());
    }

    @Async
    Slot updateSlot(Vehicle vehicle, Slot freeSlot) {
        logger.info("LogEvent.updateSlot.start : slot {}", freeSlot.getId());
        freeSlot.setState(IN_USE);
        freeSlot.setCurrentVehicle(vehicle);
        Slot finalFreeSlot = freeSlot;
        freeSlot.getRelatedFloor()
                .getSlotSet()
                .stream()
                .filter(s -> s.getState() == FREE && !s.getId().equals(finalFreeSlot.getId()))
                .findFirst()
                .ifPresentOrElse(
                        v -> logger.warn("LogEvent.updateSlotEntity.warn : there free on floor {}", v.getId()),
                        () -> finalFreeSlot.getRelatedFloor().setState(FULL));

        logger.info("LogEvent.updateSlot.end : slot {}", freeSlot.getId());
        freeSlot = slotRepository.save(freeSlot);
        return freeSlot;
    }
}
