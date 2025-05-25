package com.noom.interview.fullstack.sleep.business;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.dto.SleepLogRequest;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SleepLogCalculatorTest {

    @Test
    void testCalculateTotalTimeInBed() {
        SleepLogRequest request = new SleepLogRequest();
        request.setTimeInBedStart(LocalDateTime.of(2023, 1, 1, 22, 0));
        request.setTimeInBedEnd(LocalDateTime.of(2023, 1, 2, 6, 0));
        int totalTime = SleepLogCalculator.calculateTotalTimeInBed(request);
        assertEquals(480, totalTime); // 8 hours = 480 minutes
    }

    @Test
    void testCalculateAverageTotalTimeInBed() {
        SleepLog log1 = new SleepLog();
        log1.setTotalTimeInBed(480); // 8 hours
        SleepLog log2 = new SleepLog();
        log2.setTotalTimeInBed(540); // 9 hours
        List<SleepLog> logs = List.of(log1, log2);
        double average = SleepLogCalculator.calculateAverageTotalTimeInBed(logs);
        assertEquals(510.0, average, 0.001); // (480 + 540) / 2 = 510
    }

    @Test
    void testCalculateAverageTimeInBed() {
        SleepLog log1 = new SleepLog();
        log1.setTimeInBedStart(LocalDateTime.of(2023, 1, 1, 22, 0));
        SleepLog log2 = new SleepLog();
        log2.setTimeInBedStart(LocalDateTime.of(2023, 1, 2, 23, 0));
        List<SleepLog> logs = List.of(log1, log2);
        LocalTime averageTime = SleepLogCalculator.calculateAverageTimeInBed(logs);
        assertEquals(LocalTime.of(22, 30), averageTime); // Average of 22:00 and 23:00
    }

    @Test
    void testCalculateAverageTimeOutOfBed() {
        SleepLog log1 = new SleepLog();
        log1.setTimeInBedEnd(LocalDateTime.of(2023, 1, 2, 6, 0));
        SleepLog log2 = new SleepLog();
        log2.setTimeInBedEnd(LocalDateTime.of(2023, 1, 3, 7, 0));
        List<SleepLog> logs = List.of(log1, log2);
        LocalTime averageTime = SleepLogCalculator.calculateAverageTimeOutOfBed(logs);
        assertEquals(LocalTime.of(6, 30), averageTime); // Average of 6:00 and 7:00
    }

    @Test
    void testCalculateMorningFeelingFrequencies() {
        SleepLog log1 = new SleepLog();
        log1.setMorningFeeling(MorningFeeling.GOOD);
        SleepLog log2 = new SleepLog();
        log2.setMorningFeeling(MorningFeeling.OK);
        SleepLog log3 = new SleepLog();
        log3.setMorningFeeling(MorningFeeling.GOOD);
        List<SleepLog> logs = List.of(log1, log2, log3);
        Map<MorningFeeling, Integer> frequencies = SleepLogCalculator.calculateMorningFeelingFrequencies(logs);
        assertEquals(2, frequencies.get(MorningFeeling.GOOD));
        assertEquals(1, frequencies.get(MorningFeeling.OK));
        assertEquals(0, frequencies.get(MorningFeeling.BAD));
    }
}