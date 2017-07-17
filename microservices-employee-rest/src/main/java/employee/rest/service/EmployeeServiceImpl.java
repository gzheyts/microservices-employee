package employee.rest.service;

import employee.rest.domain.Employee;
import employee.rest.domain.EmployeeNotFoundException;
import employee.rest.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final
    EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Employee find(Long id) {
        return find0(id);
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void delete(Long id) {
        repository.delete(find0(id));
    }

    private Employee find0(Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee old = find0(id);
        BeanUtils.copyProperties(employee, old, "id");
        return repository.save(old);
    }

}
