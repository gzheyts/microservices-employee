package employee.rest.web;

import employee.rest.domain.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class EmployeeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EmployeeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleConstraintViolation(EmployeeNotFoundException ex) {
        return new ErrorMessage(ex.getLocalizedMessage());
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<ErrorMessage> handleDataException(DataAccessException ex) {
        if (DataIntegrityViolationException.class.isAssignableFrom(ex.getClass())) {
            return status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Employee info already exists"));
        }

        return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
