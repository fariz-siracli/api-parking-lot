package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.Gate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GateRepository extends CrudRepository<Gate, Long> {

}
