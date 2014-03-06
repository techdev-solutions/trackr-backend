package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.domain.Project;
import de.techdev.trackr.domain.WorkTime;
import de.techdev.trackr.repository.WorkTimeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * @author Moritz Schulze
 */
@Controller
@RequestMapping("/workTimes")
public class WorkTimeController {

    @Autowired
    @Qualifier("defaultConversionService")
    private ConversionService conversionService;

    @Autowired
    private WorkTimeRepository workTimeRepository;

    /**
     * Finds all workTimes for a project in a given interval and converts them to a mapping of employee id to worktimes.
     * @param projectId The id of the project
     * @param start The start of the interval
     * @param end The end of the interval
     * @return The mapping of employee id to a DTO object that contains the employee along the work times.
     */
    @RequestMapping(value = "/findEmployeeMappingByProjectAndDateBetween", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<Long, WorkTimeEmployee> findEmployeeMappingByProjectAndDateBetween(
            @RequestParam("project") String projectId,
            @RequestParam("start") Date start,
            @RequestParam("end") Date end) {
        //TODO: Make spring do the conversion automatically
        Project project = conversionService.convert(Long.valueOf(projectId), Project.class);
        List<WorkTime> workTimes = workTimeRepository.findByProjectAndDateBetweenOrderByDateAscStartTimeAsc(project, start, end);
        return convertStreamOfWorkTimesToMap(workTimes);
    }

    /**
     * Takes a list of workTimes, groups them by employee while transforming the WorkTime objects to CustomWorkTime DTOs and afterwards maps it
     * to a map of long (employee id) to the WorkTimeEmployee DTO.
     *
     * @param workTimes The list of worktimes to transform
     * @return The mapping of Long to WorkTimeEmployee
     */
    protected Map<Long, WorkTimeEmployee> convertStreamOfWorkTimesToMap(List<WorkTime> workTimes) {
        return workTimes.stream().collect(
                groupingBy(
                        WorkTime::getEmployee,
                        mapping(CustomWorkTime::valueOf, toList())
                )).entrySet().stream().collect(
                HashMap<Long, WorkTimeEmployee>::new,
                (resultMap, entry) -> {
                    resultMap.put(entry.getKey().getId(), WorkTimeEmployee.valueOf(entry.getKey(), entry.getValue()));
                },
                HashMap<Long, WorkTimeEmployee>::putAll);
    }

    /**
     * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
     */
    @Data
    protected static class WorkTimeEmployee {
        private Long id;
        private String name;
        private List<CustomWorkTime> workTimes = new ArrayList<>();

        public static WorkTimeEmployee valueOf(Employee employee, List<CustomWorkTime> workTimes) {
            WorkTimeEmployee workTimeEmployee = new WorkTimeEmployee();
            workTimeEmployee.id = employee.getId();
            workTimeEmployee.name = employee.getFirstName() + " " + employee.getLastName();
            workTimeEmployee.workTimes = workTimes;
            return workTimeEmployee;
        }
    }

    /**
     * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
     */
    @Data
    protected static class CustomWorkTime {
        private Date date;
        private Time start;
        private Time end;
        private String comment;

        public static CustomWorkTime valueOf(WorkTime workTime) {
            CustomWorkTime customWorkTime = new CustomWorkTime();
            customWorkTime.date = workTime.getDate();
            customWorkTime.start = workTime.getStartTime();
            customWorkTime.end = workTime.getEndTime();
            customWorkTime.comment = workTime.getComment();
            return customWorkTime;
        }
    }
}
