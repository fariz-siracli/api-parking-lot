package com.fs.api.parking.lot.dao;

import com.fs.api.parking.lot.dao.model.Floor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends CrudRepository<Floor, Long> {

    @Query(value = "select * from floor where state = :state  and height > :height " +
            "order by case when (id = :id) then 1 else 2 end", nativeQuery = true)
    List<Floor> findByStateAndHeightLessThan(String state, Integer height, Long id);
}
