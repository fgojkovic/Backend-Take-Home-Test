package com.noom.interview.fullstack.sleep.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;

public class SleepLogResponse {

    private Long userId;
    private LocalDate sleepDate;
    private LocalDateTime timeInBedStart;
    private LocalDateTime timeInBedEnd;
    private Integer totalTimeInBed;
    private MorningFeeling morningFeeling;

    public SleepLogResponse() {

    }

    // Constructor
    public SleepLogResponse(Long userId, LocalDate sleepDate, LocalDateTime timeInBedStart,
            LocalDateTime timeInBedEnd,
            Integer totalTimeInBed, MorningFeeling morningFeeling) {
        this.userId = userId;
        this.sleepDate = sleepDate;
        this.timeInBedStart = timeInBedStart;
        this.timeInBedEnd = timeInBedEnd;
        this.totalTimeInBed = totalTimeInBed;
        this.morningFeeling = morningFeeling;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(LocalDate sleepDate) {
        this.sleepDate = sleepDate;
    }

    public LocalDateTime getTimeInBedStart() {
        return timeInBedStart;
    }

    public void setTimeInBedStart(LocalDateTime timeInBedStart) {
        this.timeInBedStart = timeInBedStart;
    }

    public LocalDateTime getTimeInBedEnd() {
        return timeInBedEnd;
    }

    public void setTimeInBedEnd(LocalDateTime timeInBedEnd) {
        this.timeInBedEnd = timeInBedEnd;
    }

    public Integer getTotalTimeInBed() {
        return totalTimeInBed;
    }

    public void setTotalTimeInBed(Integer totalTimeInBed) {
        this.totalTimeInBed = totalTimeInBed;
    }

    public MorningFeeling getMorningFeeling() {
        return morningFeeling;
    }

    public void setMorningFeeling(MorningFeeling morningFeeling) {
        this.morningFeeling = morningFeeling;
    }
}