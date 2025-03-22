package com.project.moflis.recurringschedules.controller;

import com.project.moflis.recurringschedules.dto.AddRecurringScheduleRequest;
import com.project.moflis.recurringschedules.dto.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.dto.UpdateRecurringScheduleRequest;
import com.project.moflis.recurringschedules.service.RecurringScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedules/recurring-schedule")
public class RecurringScheduleController {

    private final RecurringScheduleService recurringScheduleService;

    public RecurringScheduleController(RecurringScheduleService recurringScheduleService) {
        this.recurringScheduleService = recurringScheduleService;
    }

    @PostMapping
    public ResponseEntity<RecurringScheduleResponse> addRecurringSchedule(AddRecurringScheduleRequest request) {
        RecurringScheduleResponse response = recurringScheduleService.addRecurringSchedule(request.toCommand(request));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{recurringScheduleId}")
    public ResponseEntity<RecurringScheduleResponse> patchRecurringSchedule(
            @PathVariable("recurringScheduleId") int recurringScheduleId, UpdateRecurringScheduleRequest request) {
        RecurringScheduleResponse response = recurringScheduleService.patchRecurringSchedule(recurringScheduleId, request.toCommand(request));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{recurringScheduleId}")
    public ResponseEntity<RecurringScheduleResponse> deleteRecurringSchedule(@PathVariable("recurringScheduleId") int recurringScheduleId) {
        RecurringScheduleResponse response = recurringScheduleService.deleteRecurringSchedule(recurringScheduleId);
        return ResponseEntity.ok(response);
    }

}
