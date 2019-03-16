package mjchang.spring_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mjchang.spring_api.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
