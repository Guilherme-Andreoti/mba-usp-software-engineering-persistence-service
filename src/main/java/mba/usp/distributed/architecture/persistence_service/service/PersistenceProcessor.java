package mba.usp.distributed.architecture.persistence_service.service;

import mba.usp.distributed.architecture.persistence_service.model.SensorData;
import mba.usp.distributed.architecture.persistence_service.repositories.SensorDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PersistenceProcessor {


    private final SensorDataRepository sensorDataRepository;

    public PersistenceProcessor(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public void process(SensorData data) {
        sensorDataRepository.save(data);
    }

}
