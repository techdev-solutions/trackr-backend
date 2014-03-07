package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.domain.Project;
import de.techdev.trackr.domain.WorkTime;
import de.techdev.trackr.repository.WorkTimeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
     *
     * @param projectId The id of the project
     * @param start     The start of the interval
     * @param end       The end of the interval
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
                    Link link = repositoryEntityLinks.linkToSingleResource(Employee.class, entry.getKey().getId());
                    WorkTimeEmployee workTimeEmployee = WorkTimeEmployee.valueOf(entry.getKey(), entry.getValue());
                    workTimeEmployee.add(link.withSelfRel());
                    resultMap.put(entry.getKey().getId(), workTimeEmployee);
                },
                HashMap<Long, WorkTimeEmployee>::putAll);
    }

    @Autowired
    private EntityLinks repositoryEntityLinks;

    /**
     * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
     * <p>
     * It extends {@link org.springframework.hateoas.ResourceSupport} so a link to the employee entity can be added.
     */
    @Data
    protected static class WorkTimeEmployee extends ResourceSupport {
        private String name;
        private List<CustomWorkTime> workTimes;

        /**
         * Create a WorkTimeEmployee out of an Employee and a list of workTimes. It is the responsibility of the caller to
         * assure that the workTimes belong to the employee.
         * <p>
         * This method will aggregate the workTimes by date and sum up the worked hours.
         *
         * @param employee  The employee to use
         * @param workTimes The list of workTimes to use.
         * @return A workTime employee
         */
        public static WorkTimeEmployee valueOf(Employee employee, List<CustomWorkTime> workTimes) {
            WorkTimeEmployee workTimeEmployee = new WorkTimeEmployee();
            workTimeEmployee.name = employee.getFirstName() + " " + employee.getLastName();
            workTimeEmployee.workTimes = reduceAndSortWorktimes(workTimes);
            return workTimeEmployee;
        }

        /**
         * Add up work times that belong to the same date.
         *
         * @param workTimes The worktimes to add
         * @return A sorted list of worktimes.
         */
        protected static List<CustomWorkTime> reduceAndSortWorktimes(List<CustomWorkTime> workTimes) {
            CustomWorkTime identity = new CustomWorkTime();
            identity.setMinutes(0L);
            Map<Date, CustomWorkTime> mapped = workTimes.stream().collect(groupingBy(CustomWorkTime::getDate, reducing(identity, CustomWorkTime::addOtherWorkTime)));
            return mapped.values().stream().sorted().collect(Collectors.toList());
        }
    }

    /**
     * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
     */
    @Data
    protected static class CustomWorkTime implements Comparable<CustomWorkTime> {
        private Date date;
        private Long minutes;

        public CustomWorkTime addOtherWorkTime(CustomWorkTime other) {
            CustomWorkTime added = new CustomWorkTime();
            added.setDate(other.getDate());
            added.setMinutes(this.getMinutes() + other.getMinutes());
            return added;
        }

        public static CustomWorkTime valueOf(WorkTime workTime) {
            CustomWorkTime customWorkTime = new CustomWorkTime();
            customWorkTime.minutes = Duration.between(workTime.getStartTime().toLocalTime(), workTime.getEndTime().toLocalTime()).toMinutes();
            customWorkTime.date = workTime.getDate();
            return customWorkTime;
        }

        @Override
        public int compareTo(CustomWorkTime o) {
            return this.getDate().compareTo(o.getDate());
        }
    }
}
