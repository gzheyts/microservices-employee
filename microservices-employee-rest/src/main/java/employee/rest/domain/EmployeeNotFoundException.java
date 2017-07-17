package employee.rest.domain;

import java.text.MessageFormat;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(final Long id) {
        super(MessageFormat.format("Employee with id={0} not found", id));
    }
}
