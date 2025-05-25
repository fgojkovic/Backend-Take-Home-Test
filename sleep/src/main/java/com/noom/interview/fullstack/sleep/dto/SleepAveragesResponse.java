package com.noom.interview.fullstack.sleep.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;

public class SleepAveragesResponse {

    private LocalDate dateRangeStart;
    private LocalDate dateRangeEnd;
    private Double averageTotalTimeInBed;
    private LocalTime averageTimeInBed;
    private LocalTime averageTimeOutOfBed;
    private Map<MorningFeeling, Integer> morningFeelingFrequencies;

    public SleepAveragesResponse() {
    }

    public SleepAveragesResponse(LocalDate dateRangeStart, LocalDate dateRangeEnd, Double averageTotalTimeInBed,
            LocalTime averageTimeInBed, LocalTime averageTimeOutOfBed,
            Map<MorningFeeling, Integer> morningFeelingFrequencies) {
        this.dateRangeStart = dateRangeStart;
        this.dateRangeEnd = dateRangeEnd;
        this.averageTotalTimeInBed = averageTotalTimeInBed;
        this.averageTimeInBed = averageTimeInBed;
        this.averageTimeOutOfBed = averageTimeOutOfBed;
        this.morningFeelingFrequencies = morningFeelingFrequencies;
    }

    public LocalDate getDateRangeStart() {
        return dateRangeStart;
    }

    public void setDateRangeStart(LocalDate dateRangeStart) {
        this.dateRangeStart = dateRangeStart;
    }

    public LocalDate getDateRangeEnd() {
        return dateRangeEnd;
    }

    public void setDateRangeEnd(LocalDate dateRangeEnd) {
        this.dateRangeEnd = dateRangeEnd;
    }

    public Double getAverageTotalTimeInBed() {
        return averageTotalTimeInBed;
    }

    public void setAverageTotalTimeInBed(Double averageTotalTimeInBed) {
        this.averageTotalTimeInBed = averageTotalTimeInBed;
    }

    public LocalTime getAverageTimeInBed() {
        return averageTimeInBed;
    }

    public void setAverageTimeInBed(LocalTime averageTimeInBed) {
        this.averageTimeInBed = averageTimeInBed;
    }

    public LocalTime getAverageTimeOutOfBed() {
        return averageTimeOutOfBed;
    }

    public void setAverageTimeOutOfBed(LocalTime averageTimeOutOfBed) {
        this.averageTimeOutOfBed = averageTimeOutOfBed;
    }

    public Map<MorningFeeling, Integer> getMorningFeelingFrequencies() {
        return morningFeelingFrequencies;
    }

    public void setMorningFeelingFrequencies(Map<MorningFeeling, Integer> morningFeelingFrequencies) {
        this.morningFeelingFrequencies = morningFeelingFrequencies;
    }
}