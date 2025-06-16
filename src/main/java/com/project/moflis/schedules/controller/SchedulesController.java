package com.project.moflis.schedules.controller;

import com.project.moflis.global.security.model.CustomUserDetails;
import com.project.moflis.schedules.dto.page.PageDTO;
import com.project.moflis.schedules.dto.schedules.AddSchedulsRequest;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.dto.schedules.ScheduleSummaryResponse;
import com.project.moflis.schedules.dto.schedules.UpdateSchedulsRequest;
import com.project.moflis.schedules.service.SchedulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedules")
public class SchedulesController {

    private final SchedulesService schedulsService;

    public SchedulesController(SchedulesService schedulsService) {
        this.schedulsService = schedulsService;
    }

    //스케줄 조회
    @GetMapping
    public ResponseEntity<PageDTO<ScheduleSummaryResponse>> getSchedules(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = user.getId();
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
    @PostMapping
    public ResponseEntity<ScheduleResponse> addSchedules(AddSchedulsRequest request) {
        ScheduleResponse response = schedulsService.addSchedules(request.toCommand());
        return ResponseEntity.ok(response);
    }

    //스케줄 수정
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> patchSchedules(@PathVariable("scheduleId") Long scheduleId, UpdateSchedulsRequest request) {
        ScheduleResponse response = schedulsService.updateSchedules(request.toCommand(scheduleId));
        return ResponseEntity.ok(response);
    }

    //스케줄 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedules(@PathVariable("scheduleId") Long scheduleId) {
        schedulsService.deleteSchedules(scheduleId);
        return ResponseEntity.noContent().build();
    }

}
