package employee.report.web;

import employee.report.domain.EmployeeReport;
import employee.report.domain.EmployeeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@RequestMapping("/employee/report")
@RestController
public class EmployeeReportController {

    private final EmployeeReportService reportService;

    @Autowired
    public EmployeeReportController(EmployeeReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeReport>> getAll(Pageable pageable) {
        return ResponseEntity.ok(reportService.findAll(pageable));
    }

}
