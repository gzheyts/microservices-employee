package employee.report.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EmployeeReport {
    @Id
    @SequenceGenerator(name = "employee_report_generator", sequenceName = "employee_report_sequence")
    @GeneratedValue(generator = "employee_report_generator")
    private Long id;
    @CreatedDate
    private Date reportDate;
    private String reportName;
    private String reportValue;
}
