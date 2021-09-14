package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.logger.DPLogger;
import com.fs.api.parking.lot.dao.SlotRepository;
import com.fs.api.parking.lot.dao.model.FloorEntity;
import com.fs.api.parking.lot.dao.model.GateEntity;
import com.fs.api.parking.lot.dao.model.SlotEntity;
import com.fs.api.parking.lot.dao.model.VehicleEntity;
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
    public SlotEntity slotAssignService(VehicleEntity vehicleEntity, GateEntity gateEntity) {
        logger.info("LogEvent.slotAssignService.start : vehicle {}", vehicleEntity.getId());

        List<FloorEntity> availableByHeightFloors = floorService
                .findAvailableByHeightAndWeightFloors(vehicleEntity.getHeight(), vehicleEntity.getHeight(),
                        gateEntity.getRelatedFloorEntity());
        SlotEntity freeSlot = slotSearchService.findFirstFreeSlot(availableByHeightFloors, vehicleEntity);

        freeSlot = updateSlotEntity(vehicleEntity, freeSlot);

        logger.info("LogEvent.slotAssignService.end : vehicle {}", vehicleEntity.getId());
        return freeSlot;
    }

    @Override
    public void slotFreeService(SlotEntity occupiedSlot) {
        occupiedSlot.setState(FREE);
        occupiedSlot.setCurrentVehicleEntity(null);
        if (occupiedSlot.getRelatedFloor().getState() == FULL) {
            occupiedSlot.getRelatedFloor().setState(AVAILABLE);
        }
        slotRepository.save(occupiedSlot);
    }

    @Async
    SlotEntity updateSlotEntity(VehicleEntity vehicleEntity, SlotEntity freeSlot) {
        logger.info("LogEvent.updateSlotEntity.start : slot {}", freeSlot.getId());
        freeSlot.setState(IN_USE);
        freeSlot.setCurrentVehicleEntity(vehicleEntity);
        SlotEntity finalFreeSlot = freeSlot;
        freeSlot.getRelatedFloor()
                .getSlotSet()
                .stream()
                .filter(s -> s.getState() == FREE && !s.getId().equals(finalFreeSlot.getId()))
                .findFirst()
                .ifPresentOrElse(
                        v -> logger.warn("LogEvent.updateSlotEntity.warn : there free on floor {}", v.getId()),
                        () -> finalFreeSlot.getRelatedFloor().setState(FULL));

        logger.info("LogEvent.updateSlotEntity.end : slot {}", freeSlot.getId());
        freeSlot = slotRepository.save(freeSlot);
        return freeSlot;
    }
}
