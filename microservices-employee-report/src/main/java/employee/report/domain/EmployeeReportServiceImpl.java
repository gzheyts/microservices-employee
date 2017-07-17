package employee.report.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Transactional
@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {

    private final EmployeeReportRepository reportRepository;

    @Autowired
    public EmployeeReportServiceImpl(EmployeeReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Page<EmployeeReport> findAll(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    @Override
    public EmployeeReport create(EmployeeReport report) {
        return reportRepository.save(report);
    }

}
