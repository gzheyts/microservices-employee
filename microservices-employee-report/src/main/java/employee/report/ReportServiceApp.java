package employee.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import employee.report.clientdto.PageDTO;
import employee.report.domain.EmployeeReport;
import employee.report.domain.EmployeeReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@SpringBootApplication
@EnableJpaAuditing
public class ReportServiceApp {

    @Value("${employee-rest.url}")
    private String employeeRestUrl;
    private static final String EMPLOYEE_WORK_TIME_KEY = "employeeWorkTimeInDays";
    private static final String EMPLOYEE_COUNT_KEY = "employeeCount";
    private static final Logger log = LoggerFactory.getLogger(ReportServiceApp.class);

    public static void main(String args[]) {
        SpringApplication.run(ReportServiceApp.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * Report format:
     * records: {
     * companyName: {
     * employeeCount
     * employeeWorkTimeInDays
     * }
     * }
     */
    @Bean
    public CommandLineRunner run(RestTemplate restTemplate, EmployeeReportService service) throws Exception {

        Map<String, Map<String, Object>> records = new HashMap<>();
        return args -> {
            PageDTO page = getPage(0, restTemplate);

            page.getContent().forEach(emp -> {
                Map<String, Object> meta = records.computeIfAbsent(emp.getCompany(), k -> new HashMap<>());

                meta.put(EMPLOYEE_WORK_TIME_KEY, (Long) meta.getOrDefault(EMPLOYEE_WORK_TIME_KEY, 0L)
                        + dayDiff(emp.getDismissalDate(), emp.getEmploymentDate()));
                meta.put(EMPLOYEE_COUNT_KEY, (Integer) meta.getOrDefault(EMPLOYEE_COUNT_KEY, 0) + 1);
            });

            while (page != null && page.getNumber() < page.getTotalPages()) {
                log.info(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(page.getContent()));
                page = getPage(page.getNumber() + 1, restTemplate);
            }

            EmployeeReport report = new EmployeeReport();
            report.setReportName("Average work time in company");
            report.setReportValue(new ObjectMapper().writeValueAsString(records));
            service.create(report);

        };
    }

    private static int dayDiff(Date startDate, Date endDate) {
        return ((int) ((startDate.getTime() / (24 * 60 * 60 * 1000))
                - (int) (endDate.getTime() / (24 * 60 * 60 * 1000))));
    }

    private PageDTO getPage(int pageNum, RestTemplate restTemplate) {
        return restTemplate.getForObject(MessageFormat.format(employeeRestUrl, pageNum), PageDTO.class);
    }
}