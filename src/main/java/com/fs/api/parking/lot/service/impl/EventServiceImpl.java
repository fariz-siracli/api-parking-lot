package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.DPLogger;
import com.fs.api.parking.lot.dao.ParkingEventRepository;
import com.fs.api.parking.lot.dao.model.GateEntity;
import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.dao.model.SlotEntity;
import com.fs.api.parking.lot.dao.model.TariffEntity;
import com.fs.api.parking.lot.dao.model.VehicleEntity;
import com.fs.api.parking.lot.mapper.EventMapper;
import com.fs.api.parking.lot.model.GateDto;
import com.fs.api.parking.lot.model.ParkingEventDto;
import com.fs.api.parking.lot.model.VehicleDto;
import com.fs.api.parking.lot.service.EventService;
import com.fs.api.parking.lot.service.GateService;
import com.fs.api.parking.lot.service.SlotService;
import com.fs.api.parking.lot.service.VehicleService;
import com.fs.api.parking.lot.util.EventHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class EventServiceImpl implements EventService {

    private static final DPLogger logger = DPLogger.getLogger(com.fs.api.parking.lot.DPLogger.class);

    private final GateService gateService;
    private final VehicleService vehicleService;
    private final SlotService slotService;
    private final PaymentService paymentService;
    private final TariffService tariffService;

    private final ParkingEventRepository parkingEventRepository;

    private final EventHelper eventHelper;

    public EventServiceImpl(GateService gateService, VehicleService vehicleService,
                            SlotService slotService,
                            ParkingEventRepository parkingEventRepository) {
        this.gateService = gateService;
        this.vehicleService = vehicleService;
        this.slotService = slotService;
        this.parkingEventRepository = parkingEventRepository;
    }

    @Override
    public ParkingEntryEventDto vehicleEntranceService(VehicleDto vehicleDto, GateDto gateDto) {
        logger.info("LogEvent.vehicleEntranceService.start : entry from gate {}", gateDto.getId());
        var entryGateEntity = gateService.findGateEntity(gateDto);
        var vehicle = vehicleService.saveVehicle(vehicleDto);
        var assignedSlot = slotService.slotAssignService(vehicle, entryGateEntity);
        var assignTariff = tariffService.findTariffByVehicle(vehicleDto);

        var eventDto = saveEventAndReturnDto(entryGateEntity, vehicle, assignedSlot, assignTariff);
        logger.info("LogEvent.vehicleEntranceService.end : entry from gate {}", gateDto.getId());
        return eventDto;
    }

    @Override
    public void vehicleExitService() {

    }

    private ParkingEntryEventDto saveEventAndReturnDto(GateEntity entryGateEntity,
                                                       VehicleEntity vehicle,
                                                       SlotEntity assignedSlot,
                                                       TariffEntity tariffEntity) {
        logger.debug("LogEvent.saveEventAndReturnDto.start : vehicle {}", vehicle.getId());

        var date = new Date();
        var ticket = String
                .format("%d_%d_%d", assignedSlot.getRelatedFloor().getId(), assignedSlot.getId(), date.getTime());

        var event = ParkingEventEntity.builder()
                .entryGateEntity(entryGateEntity)
                .entryTime(LocalDateTime.now())
                .slotEntity(assignedSlot)
                .vehicleEntity(vehicle)
                .ticketNumber(ticket)
                .tariffEntity(tariffEntity)
                .build();
        parkingEventRepository.save(event);

        logger.debug("LogEvent.saveEventAndReturnDto.end : vehicle {}", vehicle.getId());
        return EventMapper.INSTANCE.entityToDto(event);
    }
}
