package de.techdev.trackr.domain.project.worktimes;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.Project;
import de.techdev.trackr.domain.project.billtimes.BillableTime;
import de.techdev.trackr.domain.project.billtimes.BillableTimeRepository;

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

    @Autowired
    protected EntityLinks repositoryEntityLinks;

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
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {
        //TODO: Make spring do the conversion automatically
        Project project = conversionService.convert(Long.valueOf(projectId), Project.class);
        List<WorkTime> workTimes = workTimeRepository.findByProjectAndDateBetweenOrderByDateAscStartTimeAsc(project, start, end);
        return convertStreamOfWorkTimesToMap(workTimes, getBilledMinutesMapping(start, end, project));
    }

    /**
     * Maps employees via id to the already billed times in a given interval.
     *
     * @param start   The start of the interval
     * @param end     The end of the interval
     * @param project The project
     * @return A map of employee id to a map of date to billable times.
     */
    protected Map<Long, Map<LocalDate, BillableTime>> getBilledMinutesMapping(LocalDate start, LocalDate end, Project project) {
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
     * @param workTimes            The list of worktimes to transform
     * @param billedMinutesMapping Mapping of already billed minutes
     * @return The mapping of Long to WorkTimeEmployee
     */
    protected Map<Long, WorkTimeEmployee> convertStreamOfWorkTimesToMap(List<WorkTime> workTimes, Map<Long, Map<LocalDate, BillableTime>> billedMinutesMapping) {
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
}
