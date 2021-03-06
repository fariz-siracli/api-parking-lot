package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.Tariff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends CrudRepository<Tariff, Long> {

}
