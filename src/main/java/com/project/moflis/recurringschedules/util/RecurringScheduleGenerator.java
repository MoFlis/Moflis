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

    public static List<Schedule> generateSchedules(RecurringSchedule RecurringSchedule) {
        LocalDate startDate = RecurringSchedule.getStartDate();
        LocalDate endDate = RecurringSchedule.getEndDate();
        LocalTime startTime = RecurringSchedule.getStartTime();
        LocalTime endTime = RecurringSchedule.getEndTime();

        List<Schedule> schedules = new ArrayList<>();
        LocalDate currentDate = startDate;

        switch (RecurringSchedule.getRepeatType()) {
            case DAILY:
                while (!currentDate.isAfter(endDate)) {
                    schedules.add(createSchedule(RecurringSchedule, currentDate, startTime, endTime));
                    currentDate = currentDate.plusDays(1);
                }
                break;
            case WEEKLY:
                while (!currentDate.isAfter(endDate)) {
                    schedules.add(createSchedule(RecurringSchedule, currentDate, startTime, endTime));
                    currentDate = currentDate.plusWeeks(1);
                }
                break;
            case MONTHLY:
                while (!currentDate.isAfter(endDate)) {
                    schedules.add(createSchedule(RecurringSchedule, currentDate, startTime, endTime));
                    currentDate = currentDate.plusMonths(1);
                }
                break;
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
