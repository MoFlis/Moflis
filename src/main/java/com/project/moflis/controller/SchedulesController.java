package com.project.moflis.controller;

import com.project.moflis.command.scheduls.AddSchedulsCommand;
import com.project.moflis.dto.page.PageDTO;
import com.project.moflis.dto.schedule.AddSchedulsRequest;
import com.project.moflis.dto.schedule.ScheduleResponse;
import com.project.moflis.dto.schedule.ScheduleSummaryResponse;
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
    public ResponseEntity<PageDTO<ScheduleSummaryResponse>> getSchedules(
            @PathVariable("userId") int userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageDTO<ScheduleSummaryResponse> schedulesList = schedulsService.getSchedules(userId, startDate, endDate, page, size);
        return ResponseEntity.ok(schedulesList);
    }

    //스케줄 상세 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getScheduleDetail(@PathVariable("scheduleId") int scheduleId) {
        ScheduleResponse response = schedulsService.getScheduleDetail(scheduleId);
        return ResponseEntity.ok(response);
    }

    //스케줄 추가
    @PostMapping("/users/{userId}")
    public ResponseEntity<ScheduleResponse> addSchedules(@PathVariable("userId") int userId, AddSchedulsRequest request) {
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
        ScheduleResponse response = schedulsService.addSchedules(command);
        return ResponseEntity.ok(response);
    }

    //스케줄 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> patchSchedules(@PathVariable("scheduleId") int scheduleId, UpdateSchedulsRequest request) {
        ScheduleResponse response = schedulsService.updateSchedules(request.toCommand(scheduleId));
        return ResponseEntity.ok(response);
    }

    //스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedules(@PathVariable("scheduleId") int scheduleId) {
        schedulsService.deleteSchedules(scheduleId);
        return ResponseEntity.noContent().build();
    }

}
