package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.SleepAveragesResponse;
import com.noom.interview.fullstack.sleep.dto.SleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogResponse;
import com.noom.interview.fullstack.sleep.mapper.SleepLogMapper;
import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.repository.SleepLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SleepLogServiceTest {

    @Mock
    private SleepLogRepository repository;

    @Mock
    private SleepLogMapper sleepLogMapper;

    @InjectMocks
    private SleepLogService sleepLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSleepLog_Success() {
        // Arrange
        Long userId = 1L;
        LocalDateTime start = LocalDateTime.now().minusHours(8);
        LocalDateTime end = LocalDateTime.now();
        SleepLogRequest request = new SleepLogRequest();
        request.setTimeInBedStart(start);
        request.setTimeInBedEnd(end);
        request.setMorningFeeling(MorningFeeling.GOOD);

        SleepLog sleepLog = new SleepLog();
        sleepLog.setUserId(userId);
        sleepLog.setSleepDate(end.toLocalDate());
        sleepLog.setTimeInBedStart(start);
        sleepLog.setTimeInBedEnd(end);
        sleepLog.setTotalTimeInBed((int) ChronoUnit.MINUTES.between(start, end));
        sleepLog.setMorningFeeling(MorningFeeling.GOOD);

        SleepLogResponse response = new SleepLogResponse();
        response.setUserId(userId);
        response.setSleepDate(end.toLocalDate());
        response.setTimeInBedStart(start);
        response.setTimeInBedEnd(end);
        response.setTotalTimeInBed((int) ChronoUnit.MINUTES.between(start, end));
        response.setMorningFeeling(MorningFeeling.GOOD);

        when(repository.findByUserIdAndSleepDate(userId, end.toLocalDate())).thenReturn(Optional.empty());
        when(sleepLogMapper.toEntity(request, userId, end.toLocalDate(), (int) ChronoUnit.MINUTES.between(start, end)))
                .thenReturn(sleepLog);
        when(repository.save(sleepLog)).thenReturn(sleepLog);
        when(sleepLogMapper.toResponse(sleepLog)).thenReturn(response);

        // Act
        SleepLogResponse result = sleepLogService.createSleepLog(userId, request);

        // Assert
        assertThat(result).isEqualTo(response);
        verify(repository).save(sleepLog);
    }

    @Test
    public void testCreateSleepLog_AlreadyExists() {
        // Arrange
        Long userId = 1L;
        LocalDateTime start = LocalDateTime.now().minusHours(8);
        LocalDateTime end = LocalDateTime.now();
        SleepLogRequest request = new SleepLogRequest();
        request.setTimeInBedStart(start);
        request.setTimeInBedEnd(end);
        request.setMorningFeeling(MorningFeeling.GOOD);

        SleepLog existingLog = new SleepLog();
        existingLog.setUserId(userId);
        existingLog.setSleepDate(end.toLocalDate());

        // Mock the mapper to return a SleepLog with sleepDate set
        SleepLog sleepLog = new SleepLog();
        sleepLog.setUserId(userId);
        sleepLog.setSleepDate(end.toLocalDate());
        when(sleepLogMapper.toEntity(any(), any(), any(), anyInt())).thenReturn(sleepLog);

        when(repository.findByUserIdAndSleepDate(userId, end.toLocalDate())).thenReturn(Optional.of(existingLog));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> sleepLogService.createSleepLog(userId, request));
        verify(repository, never()).save(any());
    }

    @Test
    public void testCreateSleepLog_InvalidTime() {
        // Arrange
        Long userId = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusHours(1); // End before start
        SleepLogRequest request = new SleepLogRequest();
        request.setTimeInBedStart(start);
        request.setTimeInBedEnd(end);
        request.setMorningFeeling(MorningFeeling.GOOD);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sleepLogService.createSleepLog(userId, request));
        verify(repository, never()).findByUserIdAndSleepDate(any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    public void testGetLastNightSleep_Found() {
        // Arrange
        Long userId = 1L;
        LocalDate lastNight = LocalDate.now().minusDays(1);
        SleepLog sleepLog = new SleepLog();
        sleepLog.setUserId(userId);
        sleepLog.setSleepDate(lastNight);

        SleepLogResponse response = new SleepLogResponse();
        response.setUserId(userId);
        response.setSleepDate(lastNight);

        when(repository.findByUserIdAndSleepDate(userId, lastNight)).thenReturn(Optional.of(sleepLog));
        when(sleepLogMapper.toResponse(sleepLog)).thenReturn(response);

        // Act
        SleepLogResponse result = sleepLogService.getLastNightSleep(userId);

        // Assert
        assertThat(result).isEqualTo(response);
    }

    @Test
    public void testGetLastNightSleep_NotFound() {
        // Arrange
        Long userId = 1L;
        LocalDate lastNight = LocalDate.now().minusDays(1);

        when(repository.findByUserIdAndSleepDate(userId, lastNight)).thenReturn(Optional.empty());

        // Act
        SleepLogResponse result = sleepLogService.getLastNightSleep(userId);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void testGetLast30DayAverages_WithLogs() {
        // Arrange
        Long userId = 1L;
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        SleepLog log1 = new SleepLog();
        log1.setUserId(userId);
        log1.setSleepDate(endDate.minusDays(1));
        log1.setTimeInBedStart(LocalDateTime.of(endDate.minusDays(1), LocalTime.of(22, 0)));
        log1.setTimeInBedEnd(LocalDateTime.of(endDate.minusDays(1), LocalTime.of(6, 0)));
        log1.setTotalTimeInBed(480);
        log1.setMorningFeeling(MorningFeeling.GOOD);

        SleepLog log2 = new SleepLog();
        log2.setUserId(userId);
        log2.setSleepDate(endDate);
        log2.setTimeInBedStart(LocalDateTime.of(endDate, LocalTime.of(23, 0)));
        log2.setTimeInBedEnd(LocalDateTime.of(endDate, LocalTime.of(7, 0)));
        log2.setTotalTimeInBed(480);
        log2.setMorningFeeling(MorningFeeling.OK);

        List<SleepLog> logs = Arrays.asList(log1, log2);
        when(repository.findByUserIdAndSleepDateBetween(userId, startDate, endDate)).thenReturn(logs);

        // Mock the mapper response
        SleepAveragesResponse mockResponse = new SleepAveragesResponse();
        mockResponse.setDateRangeStart(startDate);
        mockResponse.setDateRangeEnd(endDate);
        mockResponse.setAverageTotalTimeInBed(480.0);
        mockResponse.setAverageTimeInBed(LocalTime.of(22, 30));
        mockResponse.setAverageTimeOutOfBed(LocalTime.of(6, 30));
        mockResponse.setMorningFeelingFrequencies(new EnumMap<>(Map.of(
                MorningFeeling.GOOD, 1,
                MorningFeeling.OK, 1,
                MorningFeeling.BAD, 0)));

        when(sleepLogMapper.toAverageResponse(
                eq(startDate),
                eq(endDate),
                anyDouble(),
                any(LocalTime.class),
                any(LocalTime.class),
                anyMap())).thenReturn(mockResponse);

        // Act
        SleepAveragesResponse result = sleepLogService.getLast30DayAverages(userId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getDateRangeStart()).isEqualTo(startDate);
        assertThat(result.getDateRangeEnd()).isEqualTo(endDate);
        assertThat(result.getAverageTotalTimeInBed()).isEqualTo(480.0);
        assertThat(result.getAverageTimeInBed()).isEqualTo(LocalTime.of(22, 30));
        assertThat(result.getAverageTimeOutOfBed()).isEqualTo(LocalTime.of(6, 30));
        assertThat(result.getMorningFeelingFrequencies()).containsEntry(MorningFeeling.GOOD, 1);
        assertThat(result.getMorningFeelingFrequencies()).containsEntry(MorningFeeling.OK, 1);
        assertThat(result.getMorningFeelingFrequencies()).containsEntry(MorningFeeling.BAD, 0);
    }

    @Test
    public void testGetLast30DayAverages_NoLogs() {
        // Arrange
        Long userId = 1L;
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        when(repository.findByUserIdAndSleepDateBetween(userId, startDate, endDate)).thenReturn(List.of());

        SleepAveragesResponse response = new SleepAveragesResponse();
        response.setDateRangeStart(startDate);
        response.setDateRangeEnd(endDate);
        response.setAverageTotalTimeInBed(0.0);
        response.setAverageTimeInBed(LocalTime.of(0, 0));
        response.setAverageTimeOutOfBed(LocalTime.of(0, 0));
        response.setMorningFeelingFrequencies(Map.of(
                MorningFeeling.GOOD, 0,
                MorningFeeling.OK, 0,
                MorningFeeling.BAD, 0));

        when(sleepLogMapper.toAverageResponseWithEmptyLogs(startDate, endDate)).thenReturn(response);

        // Act
        SleepAveragesResponse result = sleepLogService.getLast30DayAverages(userId);

        // Assert
        assertThat(result.getAverageTotalTimeInBed()).isEqualTo(0.0);
        assertThat(result.getAverageTimeInBed()).isEqualTo(LocalTime.of(0, 0));
        assertThat(result.getAverageTimeOutOfBed()).isEqualTo(LocalTime.of(0, 0));
        assertThat(result.getMorningFeelingFrequencies()).containsEntry(MorningFeeling.GOOD, 0);
        assertThat(result.getMorningFeelingFrequencies()).containsEntry(MorningFeeling.OK, 0);
        assertThat(result.getMorningFeelingFrequencies()).containsEntry(MorningFeeling.BAD, 0);
    }
}