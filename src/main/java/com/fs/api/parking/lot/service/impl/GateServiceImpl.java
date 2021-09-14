package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.DPLogger;
import com.fs.api.parking.lot.dao.GateRepository;
import com.fs.api.parking.lot.dao.model.GateEntity;
import com.fs.api.parking.lot.exception.DPException;
import com.fs.api.parking.lot.model.GateDto;
import com.fs.api.parking.lot.service.GateService;
import org.springframework.stereotype.Service;

@Service
public class GateServiceImpl implements GateService {

    private static final DPLogger logger = DPLogger.getLogger(GateServiceImpl.class);

    private final GateRepository gateRepository;

    public GateServiceImpl(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    @Override
    public GateEntity findGateEntity(GateDto dto) {
        logger.info("LogEvent.findGateEntity.start : gate {}", dto.getId());

        var gateEntityOpt = gateRepository.findById(dto.getId());
        if (gateEntityOpt.isEmpty()) {
            logger.error("LogEvent.findGateEntity.start : gate {}", dto.getId());
            throw new DPException("exception.parking-lot.gate-not-found", "Requested Gate not found");
        }

        logger.info("LogEvent.findGateEntity.end : gate {}", dto.getId());
        return gateEntityOpt.get();
    }
}
