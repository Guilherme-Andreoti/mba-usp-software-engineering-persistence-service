package mba.usp.distributed.architecture.persistence_service.messaging;

import io.micrometer.core.annotation.Timed;
import mba.usp.distributed.architecture.persistence_service.model.SensorData;
import mba.usp.distributed.architecture.persistence_service.services.PersistenceProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProcessedDataListener {

    private final PersistenceProcessor processor;

    public ProcessedDataListener(PersistenceProcessor processor) {
        this.processor = processor;
    }

    @RabbitListener(queues = "persistence.data.queue")
    public void handleSensorData(SensorData message) {
        try {
            System.out.println(message);
            processor.process(message);
        } catch (Exception e) {
            System.err.println("❌ Falha ao salvar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
