package de.techdev.trackr.domain.project.worktimes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.hateoas.ResourceSupport;

import de.techdev.trackr.domain.employee.Employee;
import de.techdev.trackr.domain.project.billtimes.BillableTime;

/**
 * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
 * <p>
 * It extends {@link org.springframework.hateoas.ResourceSupport} so a link to the employee entity can be added.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkTimeEmployee extends ResourceSupport {
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
        workTimeEmployee.workTimes = CustomWorkTime.reduceAndSortWorktimes(workTimes);
        return workTimeEmployee;
    }

    public void addBilledMinutes(Map<LocalDate, BillableTime> dateBillableTimeMapping) {
        workTimes.forEach(ctw -> {
            if(dateBillableTimeMapping != null && dateBillableTimeMapping.get(ctw.getDate()) != null) {
                ctw.setBilledTimeId(dateBillableTimeMapping.get(ctw.getDate()).getId());
                ctw.setHours(dateBillableTimeMapping.get(ctw.getDate()).getMinutes().doubleValue() / 60);
            }
        });
    }

}
