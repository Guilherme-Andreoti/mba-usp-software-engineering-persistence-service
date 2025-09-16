package mba.usp.distributed.architecture.persistence_service.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import mba.usp.distributed.architecture.persistence_service.model.SensorData;
import mba.usp.distributed.architecture.persistence_service.repositories.SensorDataRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PersistenceProcessor {
    private final SensorDataRepository sensorDataRepository;
    private final Counter messageCounter;
    private final MeterRegistry registry;


    public PersistenceProcessor(SensorDataRepository sensorDataRepository,MeterRegistry registry) {
        this.registry = registry;
        this.messageCounter = Counter.builder("persisted-records")
                .tag("service", "microservices")
                .description("Number of MQTT messages received")
                .register(registry);

        this.sensorDataRepository = sensorDataRepository;
    }

    public void process(SensorData data) {
        sensorDataRepository.save(data);
        messageCounter.increment();

        long startMillis = (long) data.getStartProcessingTimestamp();
        long nowMillis = System.currentTimeMillis();
        long durationMillis = nowMillis - startMillis;
        Timer.builder("processing_time")
                .description("Total time taken from ingestion to completion")
                .tags("service", "microservices")
                .register(registry)
                .record(durationMillis, TimeUnit.MILLISECONDS);
    }

}
