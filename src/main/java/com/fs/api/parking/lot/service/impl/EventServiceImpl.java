package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.dao.ParkingEventRepository;
import com.fs.api.parking.lot.dao.model.GateEntity;
import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.dao.model.SlotEntity;
import com.fs.api.parking.lot.dao.model.TariffEntity;
import com.fs.api.parking.lot.dao.model.VehicleEntity;
import com.fs.api.parking.lot.exception.DPException;
import com.fs.api.parking.lot.logger.DPLogger;
import com.fs.api.parking.lot.mapper.EventMapper;
import com.fs.api.parking.lot.model.ExitRequest;
import com.fs.api.parking.lot.model.GateDto;
import com.fs.api.parking.lot.model.ParkingEntryEventDto;
import com.fs.api.parking.lot.model.ParkingExitEventDto;
import com.fs.api.parking.lot.model.VehicleDto;
import com.fs.api.parking.lot.service.EventService;
import com.fs.api.parking.lot.service.GateService;
import com.fs.api.parking.lot.service.PaymentService;
import com.fs.api.parking.lot.service.SlotService;
import com.fs.api.parking.lot.service.TariffService;
import com.fs.api.parking.lot.service.VehicleService;
import com.fs.api.parking.lot.util.EventHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.fs.api.parking.lot.model.enums.PaymentStatus.SUCCESS;

@Service
public class EventServiceImpl implements EventService {

    private static final DPLogger logger = DPLogger.getLogger(DPLogger.class);

    private final GateService gateService;
    private final VehicleService vehicleService;
    private final SlotService slotService;
    private final PaymentService paymentService;
    private final TariffService tariffService;

    private final ParkingEventRepository parkingEventRepository;

    private final EventHelper eventHelper;

    public EventServiceImpl(GateService gateService, VehicleService vehicleService,
                            SlotService slotService,
                            PaymentService paymentService,
                            TariffService tariffService,
                            ParkingEventRepository parkingEventRepository,
                            EventHelper eventHelper) {
        this.gateService = gateService;
        this.vehicleService = vehicleService;
        this.slotService = slotService;
        this.paymentService = paymentService;
        this.tariffService = tariffService;
        this.parkingEventRepository = parkingEventRepository;
        this.eventHelper = eventHelper;
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
    public ParkingExitEventDto vehicleExitService(ExitRequest request) {
        logger.info("LogEvent.vehicleExitService.start : exit from gate {}, ticket {}",
                request.getExitGateDto().getId(), request.getTicket());

        var parkingEvent = eventHelper.findEventByTicket(request.getTicket());
        var payment = paymentService.findPaymentByEvent(parkingEvent);

        if (payment.getPaymentStatus() != SUCCESS) {
            throw new DPException("exception.parking-lot.success-payment-not-found", "Success payment not found");
        }

        slotService.slotFreeService(parkingEvent.getSlotEntity());

        var response = updateParkingEvent(request, parkingEvent);
        logger.info("LogEvent.vehicleExitService.end : exit from gate {}, ticket {}",
                request.getExitGateDto().getId(), request.getTicket());

        return response;
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

    private ParkingExitEventDto updateParkingEvent(ExitRequest request, ParkingEventEntity parkingEvent) {
        logger.debug("LogEvent.updateParkingEvent.start : event {}", parkingEvent.getId());

        var exitGateEntity = gateService.findGateEntity(request.getExitGateDto());
        parkingEvent.setExitGateEntity(exitGateEntity);
        parkingEvent.setExitTime(LocalDateTime.now());
        parkingEventRepository.save(parkingEvent);

        var parkingDto = ParkingExitEventDto.builder()
                .ticketNumber(parkingEvent.getTicketNumber())
                .exitTime(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(parkingEvent.getExitTime()))
                .build();

        logger.debug("LogEvent.updateParkingEvent.end : event {}", parkingEvent.getId());
        return parkingDto;
    }
}
