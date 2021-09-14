package com.fs.api.parking.lot.mapper;

import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.model.ParkingEntryEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PaymentMapper.class})
public abstract class EventMapper {

    public static final EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mappings({
            @Mapping(target = "pricePerMinute", source = "eventEntity.tariffEntity.price"),
            @Mapping(target = "vehicleDto.height", source = "eventEntity.vehicleEntity.height"),
            @Mapping(target = "vehicleDto.weight", source = "eventEntity.vehicleEntity.weight"),
            @Mapping(target = "slotInfoDto.floorNumber", source = "eventEntity.slotEntity.relatedFloor.name"),
            @Mapping(target = "slotInfoDto.slotNumber", source = "eventEntity.slotEntity.number"),
            @Mapping(target = "entryTime", source = "eventEntity.entryTime", qualifiedByName = "convertTime"),
    })
    public abstract ParkingEntryEventDto entityToDto(ParkingEventEntity eventEntity);
}
