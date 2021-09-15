package com.fs.api.parking.lot.service.impl;

import com.fs.api.parking.lot.dao.GateRepository;
import com.fs.api.parking.lot.dao.model.Gate;
import com.fs.api.parking.lot.exception.DPException;
import com.fs.api.parking.lot.logger.DPLogger;
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
    public Gate find(GateDto dto) {
        logger.info("LogEvent.findGateEntity.start : gate {}", dto.getId());

        var gateEntityOpt = gateRepository.findById(dto.getId());

        return gateEntityOpt.orElseThrow(() -> {
            logger.error("LogEvent.findGateEntity.start : gate {}", dto.getId());
            throw new DPException("exception.parking-lot.gate-not-found", "Requested Gate not found");
        });
    }
}
