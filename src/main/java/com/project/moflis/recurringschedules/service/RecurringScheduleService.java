package com.project.moflis.recurringschedules.service;

import com.project.moflis.recurringschedules.command.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.command.UpdateRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.dto.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.recurringschedules.mapper.RecurringScheduleMapper;
import com.project.moflis.recurringschedules.repository.RecurringSchedulesRepository;
import com.project.moflis.recurringschedules.util.RecurringScheduleGenerator;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.schedules.enums.SchedulesStatus;
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

    @Transactional
    public RecurringScheduleResponse patchRecurringSchedule(int recurringScheduleId, UpdateRecurringSchedulesCommand command) {
        RecurringSchedules recurringSchedules = recurringSchedulesRepository.findById(recurringScheduleId)
                .orElseThrow(() -> new RuntimeException("반복일정 아이디가 존재하지 않습니다"));
        recurringSchedules.updateRecurringSchedules(command);
        schedulesService.updateRecurringSchedules(recurringSchedules, recurringScheduleId);
        return RecurringScheduleMapper.INSTANCE.toRecurringScheduleResponse(recurringSchedulesRepository.save(recurringSchedules));

    }

    @Transactional
    public RecurringScheduleResponse deleteRecurringSchedule(int recurringScheduleId) {
        RecurringSchedules recurringSchedules = recurringSchedulesRepository.findById(recurringScheduleId)
                .orElseThrow(() -> new RuntimeException("반복일정 아이디가 존재하지 않습니다."));
        recurringSchedules.setStatus(SchedulesStatus.INACTIVE);
        schedulesService.deleteRecurringSchedules(recurringScheduleId);
        return RecurringScheduleMapper.INSTANCE.toRecurringScheduleResponse(recurringSchedulesRepository.save(recurringSchedules));
    }

}
