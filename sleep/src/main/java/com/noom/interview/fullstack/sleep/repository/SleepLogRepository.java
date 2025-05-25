package com.noom.interview.fullstack.sleep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noom.interview.fullstack.sleep.model.SleepLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {
    Optional<SleepLog> findByUserIdAndSleepDate(Long userId, LocalDate sleepDate);

    @Query("SELECT sl FROM SleepLog sl WHERE sl.userId = :userId AND sl.sleepDate BETWEEN :startDate AND :endDate")
    List<SleepLog> findByUserIdAndSleepDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}