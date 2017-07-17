package employee.rest.service;

import employee.rest.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public interface EmployeeService {

    Page<Employee> findAll(Pageable pageable);

    Employee find(Long id);

    Employee save(Employee employee);

    void delete(Long id);

    Employee update(Long id, Employee employee);

}
