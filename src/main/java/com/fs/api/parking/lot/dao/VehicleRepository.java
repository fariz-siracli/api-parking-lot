package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.VehicleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {
}
