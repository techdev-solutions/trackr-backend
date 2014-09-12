package de.techdev.trackr.domain.project.worktimes;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
 */
@Getter
@Setter
public class CustomWorkTime implements Comparable<CustomWorkTime> {
    private LocalDate date;
    private Long enteredMinutes;
    private Double hours;
    private Long billedTimeId;

    /**
     * Add up work times that belong to the same date.
     *
     * @param workTimes The worktimes to add
     * @return A sorted list of worktimes.
     */
    public static List<CustomWorkTime> reduceAndSortWorktimes(List<CustomWorkTime> workTimes) {
        CustomWorkTime identity = new CustomWorkTime();
        identity.setEnteredMinutes(0L);
        Map<LocalDate, CustomWorkTime> mapped = workTimes.stream().collect(groupingBy(CustomWorkTime::getDate, reducing(identity, CustomWorkTime::addOtherWorkTime)));
        return mapped.values().stream().sorted().collect(Collectors.toList());
    }

    public CustomWorkTime addOtherWorkTime(CustomWorkTime other) {
        CustomWorkTime added = new CustomWorkTime();
        added.setDate(other.getDate());
        added.setEnteredMinutes(this.getEnteredMinutes() + other.getEnteredMinutes());
        return added;
    }

    public static CustomWorkTime valueOf(WorkTime workTime) {
        CustomWorkTime customWorkTime = new CustomWorkTime();
        customWorkTime.enteredMinutes = Duration.between(workTime.getStartTime(), workTime.getEndTime()).toMinutes();
        customWorkTime.date = workTime.getDate();
        return customWorkTime;
    }

    @Override
    public int compareTo(CustomWorkTime o) {
        return this.getDate().compareTo(o.getDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomWorkTime that = (CustomWorkTime) o;

        return !(date != null ? !date.equals(that.date) : that.date != null);

    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }
}
