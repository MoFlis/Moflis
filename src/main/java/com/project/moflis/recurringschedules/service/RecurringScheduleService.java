package com.project.moflis.recurringschedules.service;

import com.project.moflis.recurringschedules.command.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.dto.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.recurringschedules.mapper.RecurringScheduleMapper;
import com.project.moflis.recurringschedules.repository.RecurringSchedulesRepository;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.entity.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecurringScheduleService {

    private final RecurringSchedulesRepository recurringSchedulesRepository;

    public RecurringScheduleService(RecurringSchedulesRepository recurringSchedulesRepository) {
        this.recurringSchedulesRepository = recurringSchedulesRepository;
    }

    public RecurringScheduleResponse addRecurringSchedule(AddRecurringSchedulesCommand command) {
        RecurringSchedules schedules = RecurringScheduleMapper.INSTANCE.toRecurringSchedule(command);
        return RecurringScheduleMapper.INSTANCE.toRecurringScheduleResponse(recurringSchedulesRepository.save(schedules));
    }

    public List<ScheduleResponse> getRecurringSchedule(int userId) {
        List<Schedule> list = recurringSchedulesRepository.findByUserId(userId);
        return RecurringScheduleMapper.INSTANCE.toSchedulesList(list);
    }
}
