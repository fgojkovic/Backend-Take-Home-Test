package com.noom.interview.fullstack.sleep.repository;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SleepLogRepositoryTest {

    @Autowired
    private SleepLogRepository repository;

    @Test
    public void testFindByUserIdAndSleepDate() {
        // Create a SleepLog with all required fields
        Long userId = 1L;
        SleepLog log = new SleepLog();
        log.setUserId(1L);
        log.setSleepDate(LocalDate.of(2023, 1, 1));
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 22, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 6, 0);
        log.setTimeInBedStart(start);
        log.setTimeInBedEnd(end);
        log.setTotalTimeInBed((int) ChronoUnit.MINUTES.between(start, end)); // Calculate and set totalTimeInBed
        log.setMorningFeeling(MorningFeeling.GOOD); // Required non-null field
        repository.save(log);

        // Test finding existing log
        Optional<SleepLog> result = repository.findByUserIdAndSleepDate(userId, LocalDate.of(2023, 1, 1));
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getUserId());
        assertEquals(LocalDate.of(2023, 1, 1), result.get().getSleepDate());
        assertEquals(480, result.get().getTotalTimeInBed()); // 8 hours = 480 minutes

        // Test finding non-existing log
        Optional<SleepLog> result2 = repository.findByUserIdAndSleepDate(userId, LocalDate.of(2023, 1, 2));
        assertFalse(result2.isPresent());
    }

    @Test
    public void testFindByUserIdAndSleepDateBetween() {
        // Create SleepLogs with all required fields
        Long userId = 1L;
        SleepLog log1 = new SleepLog();
        log1.setUserId(userId);
        log1.setSleepDate(LocalDate.of(2023, 1, 1));
        LocalDateTime start1 = LocalDateTime.of(2023, 1, 1, 22, 0);
        LocalDateTime end1 = LocalDateTime.of(2023, 1, 2, 6, 0);
        log1.setTimeInBedStart(start1);
        log1.setTimeInBedEnd(end1);
        log1.setTotalTimeInBed((int) ChronoUnit.MINUTES.between(start1, end1)); // Calculate and set totalTimeInBed
        log1.setMorningFeeling(MorningFeeling.GOOD); // Required non-null field

        SleepLog log2 = new SleepLog();
        log2.setUserId(userId);
        log2.setSleepDate(LocalDate.of(2023, 1, 2));
        LocalDateTime start2 = LocalDateTime.of(2023, 1, 2, 23, 0);
        LocalDateTime end2 = LocalDateTime.of(2023, 1, 3, 7, 0);
        log2.setTimeInBedStart(start2);
        log2.setTimeInBedEnd(end2);
        log2.setTotalTimeInBed((int) ChronoUnit.MINUTES.between(start2, end2)); // Calculate and set totalTimeInBed
        log2.setMorningFeeling(MorningFeeling.OK); // Required non-null field

        repository.saveAll(List.of(log1, log2));

        // Test finding logs within date range
        List<SleepLog> result = repository.findByUserIdAndSleepDateBetween(
                userId, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2));
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(log -> log.getSleepDate().equals(LocalDate.of(2023, 1, 1))));
        assertTrue(result.stream().anyMatch(log -> log.getSleepDate().equals(LocalDate.of(2023, 1, 2))));

        // Test finding logs with no matches
        List<SleepLog> result2 = repository.findByUserIdAndSleepDateBetween(
                userId, LocalDate.of(2023, 1, 3), LocalDate.of(2023, 1, 4));
        assertTrue(result2.isEmpty());
    }
}