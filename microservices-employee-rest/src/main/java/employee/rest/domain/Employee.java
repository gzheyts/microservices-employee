package employee.rest.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "company", "position"}))
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_generator", sequenceName = "employee_sequence")
    @GeneratedValue(generator = "employee_generator")
    private Long id;
    private String name;
    private String position;
    private String company;
    private Date employmentDate;

    @CreatedDate
    private Date createDate;
    @DateTimeFormat()
    private Date dismissalDate;
}
