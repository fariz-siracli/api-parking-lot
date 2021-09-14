package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.Slot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends CrudRepository<Slot, Long> {

}
