package benjamin.persistence;

import benjamin.connector.sonar.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectsMongoRepository extends MongoRepository<Project, String> {

}