package com.project.moflis.schedules.service;

import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.command.AddSchedulsCommand;
import com.project.moflis.schedules.command.UpdateSchedulsCommand;
import com.project.moflis.schedules.dto.page.PageDTO;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.dto.schedules.ScheduleSummaryResponse;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.schedules.mapper.ScheduleMapper;
import com.project.moflis.schedules.repository.SchedulesRepository;
import com.project.moflis.schedules.specification.ScheduleSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchedulesService {

    private final SchedulesRepository schedulesRepository;

    public SchedulesService(SchedulesRepository schedulesRepository) {
        this.schedulesRepository = schedulesRepository;
    }

    @Transactional(readOnly = true)
    public PageDTO<ScheduleSummaryResponse> getSchedules(int userId, String startDate, String endDate, int page, int size) {

        //정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduleDate").descending());

        //동적 쿼리
        Specification<Schedule> spec = Specification.where(ScheduleSpecs.hasUserId(userId))
                .and(ScheduleSpecs.dateBetween(startDate, endDate))
                .and(ScheduleSpecs.isActive());

        //조회
        Page<Schedule> schedulesPage = schedulesRepository.findAll(spec, pageable);

        // map dto 변환 시 페이징 정보를 잃지 않기 위해 씀
        return new PageDTO<>(schedulesPage.map(ScheduleMapper.INSTANCE::toScheduleSummaryResponse));
    }

    @Transactional
    public ScheduleResponse addSchedules(AddSchedulsCommand command) {
        Schedule schedule = ScheduleMapper.INSTANCE.toSchedule(command);
        return ScheduleMapper.INSTANCE.toScheduleDto(schedulesRepository.save(schedule));
    }

    @Transactional
    public ScheduleResponse updateSchedules(UpdateSchedulsCommand command) {
        Schedule schedule = schedulesRepository.findById(command.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));

        if (!schedule.isOwnedBy(command.getUserId())) {
            throw new IllegalArgumentException("해당 스케줄을 수정할 권한이 없습니다.");
        }
        schedule.updateSchedule(command);
        return ScheduleMapper.INSTANCE.toScheduleDto(schedulesRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public ScheduleResponse getScheduleDetail(int scheduleId) {
        Schedule schedule = schedulesRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다"));
        return ScheduleMapper.INSTANCE.toScheduleDto(schedule);
    }

    @Transactional
    public void deleteSchedules(int scheduleId) {
        Schedule schedule = schedulesRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다"));
        schedule.setSchedulesStatus(SchedulesStatus.DELETED);
        ScheduleMapper.INSTANCE.toScheduleDto(schedulesRepository.save(schedule));
    }


}
