package com.project.moflis.recurringschedules.util;

import com.project.moflis.recurringschedules.entity.RecurringSchedule;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RecurringScheduleGenerator {

    public static List<Schedule> generateSchedules(RecurringSchedule recurringSchedule) {
        LocalDate startDate = recurringSchedule.getStartDate();
        LocalDate endDate = recurringSchedule.getEndDate();
        LocalTime startTime = recurringSchedule.getStartTime();
        LocalTime endTime = recurringSchedule.getEndTime();

        List<Schedule> schedules = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            schedules.add(createSchedule(recurringSchedule, currentDate, startTime, endTime));
            currentDate = switch (recurringSchedule.getRepeatType()) {
                case DAILY -> currentDate.plusDays(1);
                case WEEKLY -> currentDate.plusWeeks(1);
                case MONTHLY -> currentDate.plusMonths(1);
            };
        }

        return schedules;
    }

    private static Schedule createSchedule(RecurringSchedule recurringSchedule, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return new Schedule(
                recurringSchedule.getUser(),
                recurringSchedule,
                date,
                startTime.atDate(date),
                endTime.atDate(date),
                "반복 일정: " + recurringSchedule.getDescription(),
                recurringSchedule.getDescription(),
                SchedulesStatus.ACTIVE,
                SchedulesType.PERSONAL
        );
    }

}
