package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.FloorEntity;

import java.util.List;

public interface FloorService {
    List<FloorEntity> findAvailableByHeightAndWeightFloors(int height, int weight, FloorEntity floorEntity);
}
