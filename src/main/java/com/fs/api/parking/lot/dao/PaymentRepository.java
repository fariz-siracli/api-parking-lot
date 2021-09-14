package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.ParkingEventEntity;
import com.fs.api.parking.lot.dao.model.PaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findByParkingEventEntity(ParkingEventEntity parkingEventEntity);
}
