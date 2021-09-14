package com.fs.api.parking.lot.mapper;

import com.fs.api.parking.lot.dao.model.ParkingEvent;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class EventMapperTest {

    @Test
    void entityToDto() {
        var event = EnhancedRandom.random(ParkingEvent.class);

        var actual = EventMapper.INSTANCE.entityToDto(event);

        assertEquals(actual.getVehicleDto().getHeight(), event.getVehicle().getHeight());
        assertEquals(actual.getVehicleDto().getWeight(), event.getVehicle().getWeight());
        assertEquals(actual.getPricePerMinute(), event.getTariff().getPrice().toString());
        assertEquals(actual.getSlotInfoDto().getSlotNumber(), event.getSlot().getNumber().toString());
    }
}