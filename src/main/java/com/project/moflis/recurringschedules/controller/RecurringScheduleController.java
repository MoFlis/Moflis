package com.project.moflis.recurringschedules.controller;

import com.project.moflis.recurringschedules.command.AddRecurringScheduleRequest;
import com.project.moflis.recurringschedules.command.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.service.RecurringScheduleService;
import org.springframework.http.ResponseEntity;
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
        System.out.println("Recurring Schedule added" + request);
        RecurringScheduleResponse response = recurringScheduleService.addRecurringSchedule(request.toCommand(request));
        return ResponseEntity.ok(response);
    }

}
