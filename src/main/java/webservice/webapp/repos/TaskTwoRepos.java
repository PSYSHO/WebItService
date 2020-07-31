package webservice.webapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webservice.webapp.domain.Task2;

@Repository
public interface TaskTwoRepos  extends JpaRepository<Task2,Integer> {

}
