package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.dao.model.Floor;
import com.fs.api.parking.lot.dao.model.Slot;
import com.fs.api.parking.lot.dao.model.Vehicle;
import com.fs.api.parking.lot.logger.DPLogger;
import com.fs.api.parking.lot.model.enums.SlotType;
import com.fs.api.parking.lot.service.SlotSearchService;
import com.fs.api.parking.lot.util.SlotSizeHelper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fs.api.parking.lot.model.enums.SlotState.FREE;

@Service
public class SlotSearchServiceImpl implements SlotSearchService {

    private static final DPLogger logger = DPLogger.getLogger(SlotSearchServiceImpl.class);

    private final SlotSizeHelper slotSizeHelper;

    public SlotSearchServiceImpl(SlotSizeHelper slotSizeHelper) {
        this.slotSizeHelper = slotSizeHelper;
    }

    // If Gate count is more than 2 then concurrency must be implemented

    @Override
    public Slot findFirstFreeSlot(List<Floor> floorList, Vehicle vehicle) {
        logger.info("LogEvent.findFirstFreeSlot.start : vehicle {}", vehicle.getId());

        SlotType slotType = slotSizeHelper.defineTypeByHeightAndWeight(vehicle.getHeight(), vehicle.getWeight());

        Slot freeSlot = null;
        for (Floor floor : floorList) {
            var freeSlotOptional = floor.getSlotSet()
                    .stream()
                    .filter(slot -> slot.getState() == FREE && slot.getType() == slotType)
                    .findFirst();
            if (freeSlotOptional.isPresent()) {
                freeSlot = freeSlotOptional.get();
                break;
            }
        }
        logger.info("LogEvent.findFirstFreeSlot.end : vehicle {}", vehicle.getId());
        return freeSlot;
    }

    // can be extended SlotSearchByCriteriaService
}
