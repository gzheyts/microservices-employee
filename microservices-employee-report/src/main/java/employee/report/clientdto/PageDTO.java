package employee.report.clientdto;

import lombok.Data;

import java.util.Collection;
import java.util.Map;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Data
public class PageDTO {
    private Collection<EmployeeDTO> content;
    private Boolean last;
    private Integer totalElements;
    private Integer totalPages;
    private Collection<Map<String, Object>> sort;
    private String first;
    private Integer numberOfElements;
    private Integer size;
    private Integer number;
}
