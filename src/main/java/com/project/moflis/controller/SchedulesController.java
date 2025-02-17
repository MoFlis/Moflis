package com.project.moflis.controller;

import com.project.moflis.command.scheduls.AddSchedulsCommand;
import com.project.moflis.command.scheduls.UpdateSchedulsCommand;
import com.project.moflis.dto.page.PageDTO;
import com.project.moflis.dto.schedule.AddSchedulsRequest;
import com.project.moflis.dto.schedule.ScheduleResponseDTO;
import com.project.moflis.dto.schedule.UpdateSchedulsRequest;
import com.project.moflis.enums.SchedulesStatus;
import com.project.moflis.service.SchedulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedules")
public class SchedulesController {

    private final SchedulesService schedulsService;

    public SchedulesController(SchedulesService schedulsService) {
        this.schedulsService = schedulsService;
    }

    //스케줄 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<PageDTO<ScheduleResponseDTO>> getSchedules(
            @PathVariable("userId") int userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageDTO<ScheduleResponseDTO> schedulesList = schedulsService.getSchedules(userId, startDate, endDate, page, size);
        return ResponseEntity.ok(schedulesList);
    }

    //스케줄 상세 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDTO> detailSchedules(@PathVariable("scheduleId") int scheduleId) {
        ScheduleResponseDTO response = schedulsService.detailSchedules(scheduleId);
        return ResponseEntity.ok(response);
    }

    //스케줄 추가
    @PostMapping("/users/{userId}")
    public ResponseEntity<ScheduleResponseDTO> addSchedules(@PathVariable("userId") int userId, AddSchedulsRequest request) {
        AddSchedulsCommand command = new AddSchedulsCommand(
                userId,
                request.getPostId(),
                request.getGroupPostId(),
                request.getRecurringPostId(),
                request.getScheduleDate(),
                request.getStartTime(),
                request.getEndTime(),
                request.getScheduleTitle(),
                request.getDescription(),
                SchedulesStatus.valueOf(request.getSchedulesStatus().toUpperCase())
        );
        ScheduleResponseDTO response = schedulsService.addSchedules(command);
        return ResponseEntity.ok(response);
    }

    //스케줄 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDTO> patchSchedules(@PathVariable("scheduleId") int scheduleId, UpdateSchedulsRequest request) {
        UpdateSchedulsCommand command = new UpdateSchedulsCommand(
                scheduleId,
                request.getUserId(),
                request.getPostId(),
                request.getGroupPostId(),
                request.getRecurringPostId(),
                request.getScheduleDate(),
                request.getStartTime(),
                request.getEndTime(),
                request.getScheduleTitle(),
                request.getDescription(),
                SchedulesStatus.valueOf(request.getSchedulesStatus().toUpperCase())
        );
        ScheduleResponseDTO response = schedulsService.updateSchedules(command);
        return ResponseEntity.ok(response);
    }

    //스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedules(@PathVariable("scheduleId") int scheduleId) {
        schedulsService.deleteSchedules(scheduleId);
        return ResponseEntity.noContent().build();
    }

}
