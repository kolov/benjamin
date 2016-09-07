package benjamin.persistence;


import benjamin.connector.sonar.model.Component;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComponentsRepository extends MongoRepository<Component, String> {

}