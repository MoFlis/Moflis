package com.project.moflis.recurringschedules.service;

import com.project.moflis.recurringschedules.command.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.dto.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.recurringschedules.mapper.RecurringScheduleMapper;
import com.project.moflis.recurringschedules.repository.RecurringSchedulesRepository;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.repository.SchedulesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecurringScheduleService {

    private final RecurringSchedulesRepository recurringSchedulesRepository;
    private final SchedulesRepository schedulesRepository;

    public RecurringScheduleService(RecurringSchedulesRepository recurringSchedulesRepository, SchedulesRepository schedulesRepository) {
        this.recurringSchedulesRepository = recurringSchedulesRepository;
        this.schedulesRepository = schedulesRepository;
    }

    public RecurringScheduleResponse addRecurringSchedule(AddRecurringSchedulesCommand command) {
        RecurringSchedules recurringSchedules = RecurringScheduleMapper.INSTANCE.toRecurringSchedule(command);

        LocalDate startDate = recurringSchedules.getStartDate();
        LocalDate endDate = recurringSchedules.getEndDate();
        LocalTime startTime = recurringSchedules.getStartTime();
        LocalTime endTime = recurringSchedules.getEndTime();

        recurringSchedules = recurringSchedulesRepository.save(recurringSchedules);

        List<Schedule> generatedSchedules = new ArrayList<>();
        LocalDate currentDate = startDate;

        switch (recurringSchedules.getRepeatType()) {
            case DAILY:
                while (!currentDate.isAfter(endDate)) {
                    generatedSchedules.add(createRecurringSchedule(recurringSchedules, currentDate, startTime, endTime));
                    currentDate = currentDate.plusDays(1);
                }
                break;
            case WEEKLY:
                while (!currentDate.isAfter(endDate)) {
                    generatedSchedules.add(createRecurringSchedule(recurringSchedules, currentDate, startTime, endTime));
                    currentDate = currentDate.plusWeeks(1);
                }
                break;
            case MONTHLY:
                while (!currentDate.isAfter(endDate)) {
                    generatedSchedules.add(createRecurringSchedule(recurringSchedules, currentDate, startTime, endTime));
                    currentDate = currentDate.plusMonths(1);
                }
                break;
        }
        System.out.println("개수" + generatedSchedules.size());
        schedulesRepository.saveAll(generatedSchedules);

        return RecurringScheduleMapper.INSTANCE.toRecurringScheduleResponse(null);
    }

    private Schedule createRecurringSchedule(RecurringSchedules recurringSchedules, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return new Schedule(
                recurringSchedules.getUser(),
                recurringSchedules,
                date,
                startTime.atDate(date),
                endTime.atDate(date),
                "반복 일정: " + recurringSchedules.getDescription(),
                recurringSchedules.getDescription(),
                SchedulesStatus.INACTIVE
        );
    }
}
