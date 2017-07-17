package employee.report.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

public interface EmployeeReportRepository extends JpaRepository<EmployeeReport, Long> {
}
