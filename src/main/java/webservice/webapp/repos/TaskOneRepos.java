package webservice.webapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webservice.webapp.domain.Task1;

@Repository
public interface TaskOneRepos  extends JpaRepository<Task1,Integer> {
}
