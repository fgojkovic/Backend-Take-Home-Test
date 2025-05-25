package com.noom.interview.fullstack.sleep.dto;

import java.time.LocalDateTime;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;

public class SleepLogRequest {

    private LocalDateTime timeInBedStart;
    private LocalDateTime timeInBedEnd;
    private MorningFeeling morningFeeling;

    // Getters and Setters
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

    public MorningFeeling getMorningFeeling() {
        return morningFeeling;
    }

    public void setMorningFeeling(MorningFeeling morningFeeling) {
        this.morningFeeling = morningFeeling;
    }
}