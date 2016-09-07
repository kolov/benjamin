package benjamin.persistence;


import benjamin.connector.sonar.model.Metric;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetricsRepository extends MongoRepository<Metric, String> {

}