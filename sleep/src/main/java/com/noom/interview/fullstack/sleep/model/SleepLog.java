package com.noom.interview.fullstack.sleep.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sleep_logs")
public class SleepLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "sleep_date", nullable = false)
    private LocalDate sleepDate;

    @Column(name = "time_in_bed_start", nullable = false)
    private LocalDateTime timeInBedStart;

    @Column(name = "time_in_bed_end", nullable = false)
    private LocalDateTime timeInBedEnd;

    @Column(name = "total_time_in_bed", nullable = false)
    private Integer totalTimeInBed;

    @Column(name = "morning_feeling", nullable = false)
    @Enumerated(EnumType.STRING)
    private MorningFeeling morningFeeling;

    public SleepLog() {
    }

    public SleepLog(Long userId, LocalDate sleepDate, LocalDateTime timeInBedStart, LocalDateTime timeInBedEnd,
            Integer totalTimeInBed, MorningFeeling morningFeeling) {
        this.userId = userId;
        this.sleepDate = sleepDate;
        this.timeInBedStart = timeInBedStart;
        this.timeInBedEnd = timeInBedEnd;
        this.totalTimeInBed = totalTimeInBed;
        this.morningFeeling = morningFeeling;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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