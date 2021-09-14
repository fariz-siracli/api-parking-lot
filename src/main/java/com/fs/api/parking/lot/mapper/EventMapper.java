package com.fs.api.parking.lot.mapper;

import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.model.ParkingEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class EventMapper {

    public static final EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    public abstract ParkingEventDto entityToDto(ParkingEventEntity eventEntity);
}
