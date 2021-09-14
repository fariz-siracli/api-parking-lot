package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.ParkingEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingEventRepository extends CrudRepository<ParkingEvent, Long> {
    Optional<ParkingEvent> findByTicketNumber(String ticket);
}
