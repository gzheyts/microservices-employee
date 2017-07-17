package employee.report.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

public interface EmployeeReportService {

    Page<EmployeeReport> findAll(Pageable pageable);

    EmployeeReport create(EmployeeReport report);

}
