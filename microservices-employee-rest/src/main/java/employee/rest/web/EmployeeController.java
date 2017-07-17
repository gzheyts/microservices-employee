package employee.rest.web;

import employee.rest.domain.Employee;
import employee.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@RequestMapping("employee")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAll(Pageable pageable) {
        return ok(employeeService.findAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> view(@PathVariable("id") Long id) {
        return ok(employeeService.find(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return noContent().build();
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @PostMapping("{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") Long id, @RequestBody Employee employee) {
        return status(HttpStatus.RESET_CONTENT).body(employeeService.update(id, employee));
    }
}
