package com.project.moflis.recurringschedules.util;

import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RecurringScheduleGenerator {

    public static List<Schedule> generateSchedules(RecurringSchedules recurringSchedules) {
        LocalDate startDate = recurringSchedules.getStartDate();
        LocalDate endDate = recurringSchedules.getEndDate();
        LocalTime startTime = recurringSchedules.getStartTime();
        LocalTime endTime = recurringSchedules.getEndTime();

        List<Schedule> schedules = new ArrayList<>();
        LocalDate currentDate = startDate;

        switch (recurringSchedules.getRepeatType()) {
            case DAILY:
                while (!currentDate.isAfter(endDate)) {
                    schedules.add(createSchedule(recurringSchedules, currentDate, startTime, endTime));
                    currentDate = currentDate.plusDays(1);
                }
                break;
            case WEEKLY:
                while (!currentDate.isAfter(endDate)) {
                    schedules.add(createSchedule(recurringSchedules, currentDate, startTime, endTime));
                    currentDate = currentDate.plusWeeks(1);
                }
                break;
            case MONTHLY:
                while (!currentDate.isAfter(endDate)) {
                    schedules.add(createSchedule(recurringSchedules, currentDate, startTime, endTime));
                    currentDate = currentDate.plusMonths(1);
                }
                break;
        }

        return schedules;
    }

    private static Schedule createSchedule(RecurringSchedules recurringSchedules, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return new Schedule(
                recurringSchedules.getUser(),
                recurringSchedules,
                date,
                startTime.atDate(date),
                endTime.atDate(date),
                "반복 일정: " + recurringSchedules.getDescription(),
                recurringSchedules.getDescription(),
                SchedulesStatus.INACTIVE,
                SchedulesType.PERSONAL
        );
    }

}
