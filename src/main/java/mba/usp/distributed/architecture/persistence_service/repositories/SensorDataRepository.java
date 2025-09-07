package mba.usp.distributed.architecture.persistence_service.repositories;


import mba.usp.distributed.architecture.persistence_service.model.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {

}