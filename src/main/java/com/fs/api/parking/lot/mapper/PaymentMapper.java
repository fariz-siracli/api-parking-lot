package com.fs.api.parking.lot.mapper;

import com.fs.api.parking.lot.dao.model.ParkingEvent;
import com.fs.api.parking.lot.dao.model.Payment;
import com.fs.api.parking.lot.model.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public abstract class PaymentMapper {

    public static final PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mappings({
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "paymentTime", source = "entity.createdAt", qualifiedByName = "convertTime"),
            @Mapping(target = "durationInMinutes", source = "entity.parkingEvent", qualifiedByName = "calculateDuration")
    })
    public abstract PaymentResponseDto entityToDto(Payment entity);

    @Named("convertTime")
    protected String convertTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return formatter.format(dateTime);
    }

    @Named("calculateDuration")
    protected String calculateDuration(ParkingEvent eventEntity) {
        var minutes = Duration.between(LocalDateTime.now(), eventEntity.getEntryTime()).toMinutes();

        return String.format("%d minutes", minutes);
    }
}
