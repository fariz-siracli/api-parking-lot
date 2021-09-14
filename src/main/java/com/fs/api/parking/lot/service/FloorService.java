package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.Floor;

import java.util.List;

public interface FloorService {
    List<Floor> findAvailableByHeightAndWeightFloors(int height, int weight, Floor floor);
}
