package benjamin.persistence;

import benjamin.connector.sonar.model.Project;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface ProjectsElasticRepository extends ElasticsearchCrudRepository<Project, String> {

}