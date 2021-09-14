package com.fs.api.parking.lot.util;

import com.fs.api.parking.lot.dao.ParkingEventRepository;
import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.exception.DPException;
import org.springframework.stereotype.Component;

@Component
public class EventHelper {

    private ParkingEventRepository parkingEventRepository;

    public EventHelper(ParkingEventRepository parkingEventRepository) {
        this.parkingEventRepository = parkingEventRepository;
    }

    public ParkingEventEntity findEventByTicket(String ticket) {
        var parkingEventOpt = parkingEventRepository.findByTicketNumber(ticket);
        if (parkingEventOpt.isEmpty()) {
            throw new DPException("exception.parking-lot.ticket-not-found", "Requested ticket not found");
        }

        return parkingEventOpt.get();
    }
}
