package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.Gate;
import com.fs.api.parking.lot.model.GateDto;

public interface GateService {
    Gate find(GateDto dto);
}
