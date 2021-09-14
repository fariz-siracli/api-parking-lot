package com.fs.api.parking.lot.rest;

import com.fs.api.parking.lot.model.EntranceRequest;
import com.fs.api.parking.lot.model.ParkingEventDto;
import com.fs.api.parking.lot.service.EventService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "${api.root.url}")
@Api("Event controller to handle requests for parking events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/vehicle-entry")
    public ParkingEventDto vehicleEntranceEvent(@RequestBody @NotNull EntranceRequest request) {

        return eventService.vehicleEntranceService(request.getVehicleDto(), request.getEntryGateDto());
    }
}
