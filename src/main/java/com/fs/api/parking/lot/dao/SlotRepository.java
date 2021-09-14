package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.SlotEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends CrudRepository<SlotEntity, Long> {

}
