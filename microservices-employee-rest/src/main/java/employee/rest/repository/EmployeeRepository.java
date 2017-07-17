package employee.rest.repository;

import employee.rest.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);
}

