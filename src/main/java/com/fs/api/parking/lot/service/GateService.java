package com.fs.api.parking.lot.service;

import com.fs.api.parking.lot.dao.model.GateEntity;
import com.fs.api.parking.lot.model.GateDto;

public interface GateService {
    GateEntity findGateEntity(GateDto dto);
}
