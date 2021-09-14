package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.ParkingEvent;
import com.fs.api.parking.lot.dao.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Optional<Payment> findByParkingEvent(ParkingEvent parkingEvent);
}
