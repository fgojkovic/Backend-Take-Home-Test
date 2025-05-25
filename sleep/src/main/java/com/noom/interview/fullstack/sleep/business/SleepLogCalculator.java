package com.noom.interview.fullstack.sleep.business;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.dto.SleepLogRequest;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SleepLogCalculator {

    public static int calculateTotalTimeInBed(SleepLogRequest request) {
        return (int) ChronoUnit.MINUTES.between(request.getTimeInBedStart(), request.getTimeInBedEnd());
    }

    public static double calculateAverageTotalTimeInBed(List<SleepLog> logs) {
        return logs.stream()
                .mapToInt(SleepLog::getTotalTimeInBed)
                .average()
                .orElse(0.0);
    }

    public static LocalTime calculateAverageTimeInBed(List<SleepLog> logs) {
        return LocalTime.ofSecondOfDay((long) logs.stream()
                .mapToInt(log -> log.getTimeInBedStart().toLocalTime().toSecondOfDay())
                .average()
                .orElse(0.0));
    }

    public static LocalTime calculateAverageTimeOutOfBed(List<SleepLog> logs) {
        return LocalTime.ofSecondOfDay((long) logs.stream()
                .mapToInt(log -> log.getTimeInBedEnd().toLocalTime().toSecondOfDay())
                .average()
                .orElse(0.0));
    }

    public static Map<MorningFeeling, Integer> calculateMorningFeelingFrequencies(List<SleepLog> logs) {
        Map<MorningFeeling, Integer> frequencies = new EnumMap<>(MorningFeeling.class);
        for (MorningFeeling feeling : MorningFeeling.values()) {
            frequencies.put(feeling, 0);
        }
        for (SleepLog log : logs) {
            frequencies.merge(log.getMorningFeeling(), 1, Integer::sum);
        }
        return frequencies;
    }
}