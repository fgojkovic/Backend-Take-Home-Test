package com.noom.interview.fullstack.sleep.service;

import org.springframework.stereotype.Service;

import com.noom.interview.fullstack.sleep.business.SleepLogCalculator;
import com.noom.interview.fullstack.sleep.dto.SleepAveragesResponse;
import com.noom.interview.fullstack.sleep.dto.SleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogResponse;
import com.noom.interview.fullstack.sleep.mapper.SleepLogMapper;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SleepLogService {

        private final SleepLogRepository repository;
        private final SleepLogMapper sleepLogMapper;

        public SleepLogService(SleepLogRepository repository, SleepLogMapper sleepLogMapper) {
                this.repository = repository;
                this.sleepLogMapper = sleepLogMapper;
        }

        public SleepLogResponse createSleepLog(Long userId, SleepLogRequest request) {
                int totalTimeInBed = SleepLogCalculator.calculateTotalTimeInBed(request);
                if (totalTimeInBed <= 0) {
                        throw new IllegalArgumentException("Time in bed end must be after start");
                }

                SleepLog sleepLog = sleepLogMapper.toEntity(request, userId, request.getTimeInBedEnd().toLocalDate(),
                                totalTimeInBed);

                repository.findByUserIdAndSleepDate(userId, sleepLog.getSleepDate())
                                .ifPresent(log -> {
                                        throw new IllegalStateException("Sleep log for today already exists");
                                });

                SleepLog savedLog = repository.save(sleepLog);
                return sleepLogMapper.toResponse(savedLog);
        }

        public SleepLogResponse getLastNightSleep(Long userId) {
                LocalDate lastNight = LocalDate.now().minusDays(1);
                return repository.findByUserIdAndSleepDate(userId, lastNight)
                                .map(sleepLogMapper::toResponse)
                                .orElse(null);
        }

        public SleepAveragesResponse getLast30DayAverages(Long userId) {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusDays(30);
                List<SleepLog> logs = repository.findByUserIdAndSleepDateBetween(userId, startDate, endDate);

                if (logs.isEmpty()) {
                        return sleepLogMapper.toAverageResponseWithEmptyLogs(startDate, endDate);
                }

                return sleepLogMapper.toAverageResponse(startDate,
                                endDate,
                                SleepLogCalculator.calculateAverageTotalTimeInBed(logs),
                                SleepLogCalculator.calculateAverageTimeInBed(logs),
                                SleepLogCalculator.calculateAverageTimeOutOfBed(logs),
                                SleepLogCalculator.calculateMorningFeelingFrequencies(logs));
        }
}