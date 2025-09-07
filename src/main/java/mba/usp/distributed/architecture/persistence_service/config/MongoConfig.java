package mba.usp.distributed.architecture.persistence_service.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories(basePackages = "mba.usp.distributed.architecture.persistence_service.repositories")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    private String databaseName;

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Override
    protected String getDatabaseName() {
        if (databaseName == null) {
            // Extração automática do nome do banco da URI
            String withoutParams = mongoUri.split("\\?")[0]; // remove params tipo ?retryWrites=true
            String[] parts = withoutParams.split("/");
            databaseName = parts[parts.length - 1];
        }
        return databaseName;
    }
}
