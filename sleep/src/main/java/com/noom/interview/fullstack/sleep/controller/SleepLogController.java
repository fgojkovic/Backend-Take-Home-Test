package com.noom.interview.fullstack.sleep.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.noom.interview.fullstack.sleep.dto.SleepAveragesResponse;
import com.noom.interview.fullstack.sleep.dto.SleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogResponse;
import com.noom.interview.fullstack.sleep.service.SleepLogService;

@RestController
@RequestMapping("/api/sleep")
public class SleepLogController {

    private final SleepLogService service;

    public SleepLogController(SleepLogService service) {
        this.service = service;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SleepLogResponse> createSleepLog(
            @PathVariable Long userId,
            @RequestBody SleepLogRequest request) {
        SleepLogResponse response = service.createSleepLog(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/last-night")
    public ResponseEntity<SleepLogResponse> getLastNightSleep(@PathVariable Long userId) {
        SleepLogResponse response = service.getLastNightSleep(userId);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}/averages")
    public ResponseEntity<SleepAveragesResponse> getLast30DayAverages(@PathVariable Long userId) {
        SleepAveragesResponse response = service.getLast30DayAverages(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}