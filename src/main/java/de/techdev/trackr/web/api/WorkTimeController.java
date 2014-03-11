package de.techdev.trackr.web.api;

import de.techdev.trackr.domain.BillableTime;
import de.techdev.trackr.domain.Employee;
import de.techdev.trackr.domain.Project;
import de.techdev.trackr.domain.WorkTime;
import de.techdev.trackr.repository.BillableTimeRepository;
import de.techdev.trackr.repository.WorkTimeRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private BillableTimeRepository billableTimeRepository;

    /**
     * Finds all workTimes for a project in a given interval and converts them to a mapping of employee id to worktimes.
     *
     * @param projectId The id of the project
     * @param start     The start of the interval
     * @param end       The end of the interval
     * @return The mapping of employee id to a DTO object that contains the employee along the work times.
     */
    @PreAuthorize("hasRole('ROLE_SUPERVISOR')")
    @RequestMapping(value = "/findEmployeeMappingByProjectAndDateBetween", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<Long, WorkTimeEmployee> findEmployeeMappingByProjectAndDateBetween(
            @RequestParam("project") String projectId,
            @RequestParam("start") Date start,
            @RequestParam("end") Date end) {
        //TODO: Make spring do the conversion automatically
        Project project = conversionService.convert(Long.valueOf(projectId), Project.class);
        List<WorkTime> workTimes = workTimeRepository.findByProjectAndDateBetweenOrderByDateAscStartTimeAsc(project, start, end);
        return convertStreamOfWorkTimesToMap(workTimes, getBilledMinutesMapping(start, end, project));
    }

    /**
     * Maps employees via id to the already billed times in a given interval.
     * @param start The start of the interval
     * @param end The end of the interval
     * @param project The project
     * @return A map of employee id to a map of date to billable times.
     */
    protected Map<Long, Map<Date, BillableTime>> getBilledMinutesMapping(Date start, Date end, Project project) {
        List<BillableTime> billableTimes = billableTimeRepository.findByProjectAndDateBetweenOrderByDateAsc(project, start, end);
        return billableTimes.stream().collect(
                groupingBy(bt -> bt.getEmployee().getId(),
                        mapping(billableTime -> billableTime,
                                toMap(BillableTime::getDate, Function.<BillableTime>identity()))));
    }

    /**
     * Takes a list of workTimes, groups them by employee while transforming the WorkTime objects to CustomWorkTime DTOs and afterwards maps it
     * to a map of long (employee id) to the WorkTimeEmployee DTO.
     *
     *
     * @param workTimes The list of worktimes to transform
     * @param billedMinutesMapping Mapping of already billed minutes
     * @return The mapping of Long to WorkTimeEmployee
     */
    protected Map<Long, WorkTimeEmployee> convertStreamOfWorkTimesToMap(List<WorkTime> workTimes, Map<Long, Map<Date, BillableTime>> billedMinutesMapping) {
        return workTimes.stream().collect(
                groupingBy(
                        WorkTime::getEmployee,
                        mapping(CustomWorkTime::valueOf, toList())
                )).entrySet().stream().collect(
                HashMap<Long, WorkTimeEmployee>::new,
                (resultMap, entry) -> {
                    Link link = repositoryEntityLinks.linkToSingleResource(Employee.class, entry.getKey().getId());
                    WorkTimeEmployee workTimeEmployee = WorkTimeEmployee.valueOf(entry.getKey(), entry.getValue());
                    workTimeEmployee.addBilledMinutes(billedMinutesMapping.get(entry.getKey().getId()));
                    workTimeEmployee.add(link.withSelfRel());
                    resultMap.put(entry.getKey().getId(), workTimeEmployee);
                },
                HashMap<Long, WorkTimeEmployee>::putAll);
    }

    @Autowired
    protected EntityLinks repositoryEntityLinks;

    /**
     * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
     * <p>
     * It extends {@link org.springframework.hateoas.ResourceSupport} so a link to the employee entity can be added.
     */
    @Data
    @EqualsAndHashCode(callSuper = false)
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
            workTimeEmployee.name = employee.fullName();
            workTimeEmployee.workTimes = reduceAndSortWorktimes(workTimes);
            return workTimeEmployee;
        }

        public void addBilledMinutes(Map<Date, BillableTime> dateBillableTimeMapping) {
            workTimes.forEach(ctw -> {
                if(dateBillableTimeMapping != null && dateBillableTimeMapping.get(ctw.getDate()) != null) {
                    ctw.setBilledTimeId(dateBillableTimeMapping.get(ctw.getDate()).getId());
                    ctw.setHours(dateBillableTimeMapping.get(ctw.getDate()).getMinutes().doubleValue() / 60);
                }
            });
        }

        /**
         * Add up work times that belong to the same date.
         *
         * @param workTimes The worktimes to add
         * @return A sorted list of worktimes.
         */
        protected static List<CustomWorkTime> reduceAndSortWorktimes(List<CustomWorkTime> workTimes) {
            CustomWorkTime identity = new CustomWorkTime();
            identity.setEnteredMinutes(0L);
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
        private Long enteredMinutes;
        private Double hours;
        private Long billedTimeId;

        public CustomWorkTime addOtherWorkTime(CustomWorkTime other) {
            CustomWorkTime added = new CustomWorkTime();
            added.setDate(other.getDate());
            added.setEnteredMinutes(this.getEnteredMinutes() + other.getEnteredMinutes());
            return added;
        }

        public static CustomWorkTime valueOf(WorkTime workTime) {
            CustomWorkTime customWorkTime = new CustomWorkTime();
            customWorkTime.enteredMinutes = Duration.between(workTime.getStartTime().toLocalTime(), workTime.getEndTime().toLocalTime()).toMinutes();
            customWorkTime.date = workTime.getDate();
            return customWorkTime;
        }

        @Override
        public int compareTo(CustomWorkTime o) {
            return this.getDate().compareTo(o.getDate());
        }
    }
}
