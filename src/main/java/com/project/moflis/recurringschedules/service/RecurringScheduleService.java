package com.project.moflis.recurringschedules.service;

import com.project.moflis.recurringschedules.command.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.dto.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.recurringschedules.mapper.RecurringScheduleMapper;
import com.project.moflis.recurringschedules.repository.RecurringSchedulesRepository;
import com.project.moflis.recurringschedules.util.RecurringScheduleGenerator;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.schedules.service.SchedulesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecurringScheduleService {

    private final RecurringSchedulesRepository recurringSchedulesRepository;
    private final SchedulesService schedulesService;

    public RecurringScheduleService(RecurringSchedulesRepository recurringSchedulesRepository, SchedulesService schedulesService) {
        this.recurringSchedulesRepository = recurringSchedulesRepository;
        this.schedulesService = schedulesService;
    }

    @Transactional
    public RecurringScheduleResponse addRecurringSchedule(AddRecurringSchedulesCommand command) {
        RecurringSchedules recurringSchedules = RecurringScheduleMapper.INSTANCE.toRecurringSchedule(command);
        recurringSchedules = recurringSchedulesRepository.save(recurringSchedules);

        List<Schedule> generatedSchedules = RecurringScheduleGenerator.generateSchedules(recurringSchedules);
        schedulesService.saveAllSchedules(generatedSchedules);
        return RecurringScheduleMapper.INSTANCE.toRecurringScheduleResponse(recurringSchedules);
    }

}
