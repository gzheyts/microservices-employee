package employee.report.clientdto;

import lombok.Data;

import java.util.Date;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@Data
public class EmployeeDTO {
    private String name;
    private String position;
    private String company;
    private Date employmentDate;
    private Date dismissalDate;
}
