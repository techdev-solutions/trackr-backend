package de.techdev.trackr.domain.project.worktimes;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

/**
 * DTO that contains only the needed information for the method findEmployeeMappingByProjectAndDateBetween.
 */
@Getter
@Setter
public class CustomWorkTime implements Comparable<CustomWorkTime> {
    private Date date;
    private Long enteredMinutes;
    private Double hours;
    private Long billedTimeId;
    private String comment;

    /**
     * Add up work times that belong to the same date.
     *
     * @param workTimes The worktimes to add
     * @return A sorted list of worktimes.
     */
    public static List<CustomWorkTime> reduceAndSortWorktimes(List<CustomWorkTime> workTimes) {
        CustomWorkTime identity = new CustomWorkTime();
        identity.setEnteredMinutes(0L);
        Map<Date, CustomWorkTime> mapped = workTimes.stream().collect(groupingBy(CustomWorkTime::getDate, reducing(identity, CustomWorkTime::addOtherWorkTime)));
        return mapped.values().stream().sorted().collect(Collectors.toList());
    }

    private CustomWorkTime addOtherWorkTime(CustomWorkTime other) {
        CustomWorkTime added = new CustomWorkTime();
        added.setDate(other.getDate());
        added.setEnteredMinutes(this.getEnteredMinutes() + other.getEnteredMinutes());
        added.addComment(this.getComment());
        added.addComment(other.getComment());
        return added;
    }

    private void addComment(String comment) {
        if (getComment() == null) {
            setComment(comment);
        } else if(comment != null) {
            setComment(this.comment + "\n" + comment);
        }
    }

    public static CustomWorkTime valueOf(WorkTime workTime) {
        CustomWorkTime customWorkTime = new CustomWorkTime();
        customWorkTime.enteredMinutes = Duration.between(workTime.getStartTime().toLocalTime(), workTime.getEndTime().toLocalTime()).toMinutes();
        customWorkTime.date = workTime.getDate();
        customWorkTime.comment = workTime.getComment();
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
