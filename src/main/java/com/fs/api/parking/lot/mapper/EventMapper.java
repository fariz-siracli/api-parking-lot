package com.fs.api.parking.lot.mapper;

import com.fs.api.parking.lot.dao.model.ParkingEvent;
import com.fs.api.parking.lot.model.ParkingEntryEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PaymentMapper.class})
public abstract class EventMapper {

    public static final EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mappings({
            @Mapping(target = "pricePerMinute", source = "event.getTariff.price"),
            @Mapping(target = "vehicleDto.height", source = "event.vehicle.height"),
            @Mapping(target = "vehicleDto.weight", source = "event.vehicle.weight"),
            @Mapping(target = "slotInfoDto.floorNumber", source = "event.slot.relatedFloor.name"),
            @Mapping(target = "slotInfoDto.slotNumber", source = "event.slot.number"),
            @Mapping(target = "entryTime", source = "event.entryTime", qualifiedByName = "convertTime"),
    })
    public abstract ParkingEntryEventDto entityToDto(ParkingEvent event);
}
