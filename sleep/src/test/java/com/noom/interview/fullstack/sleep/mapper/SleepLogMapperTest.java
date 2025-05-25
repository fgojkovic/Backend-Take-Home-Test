package com.noom.interview.fullstack.sleep.mapper;

import com.noom.interview.fullstack.sleep.dto.SleepAveragesResponse;
import com.noom.interview.fullstack.sleep.dto.SleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogResponse;
import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class SleepLogMapperTest {

    private SleepLogMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new SleepLogMapper();
    }

    // Tests that toRequest correctly maps a SleepLog to a SleepLogRequest.
    @Test
    public void testToRequest() {
        // Arrange
        SleepLog sleepLog = new SleepLog();
        sleepLog.setTimeInBedStart(LocalDateTime.of(2023, 10, 15, 22, 0));
        sleepLog.setTimeInBedEnd(LocalDateTime.of(2023, 10, 16, 6, 0));
        sleepLog.setMorningFeeling(MorningFeeling.GOOD);

        // Act
        SleepLogRequest request = mapper.toRequest(sleepLog);

        // Assert
        assertThat(request.getTimeInBedStart()).isEqualTo(sleepLog.getTimeInBedStart());
        assertThat(request.getTimeInBedEnd()).isEqualTo(sleepLog.getTimeInBedEnd());
        assertThat(request.getMorningFeeling()).isEqualTo(sleepLog.getMorningFeeling());
    }

    // Tests that toEntity correctly maps a SleepLogRequest and additional
    // parameters to a SleepLog.
    @Test
    public void testToEntity() {
        // Arrange
        SleepLogRequest request = new SleepLogRequest();
        request.setTimeInBedStart(LocalDateTime.of(2023, 10, 15, 22, 0));
        request.setTimeInBedEnd(LocalDateTime.of(2023, 10, 16, 6, 0));
        request.setMorningFeeling(MorningFeeling.GOOD);

        Long userId = 1L;
        LocalDate sleepDate = LocalDate.of(2023, 10, 15);
        int totalTimeInBed = 480; // 8 hours in minutes

        // Act
        SleepLog sleepLog = mapper.toEntity(request, userId, sleepDate, totalTimeInBed);

        // Assert
        assertThat(sleepLog.getUserId()).isEqualTo(userId);
        assertThat(sleepLog.getSleepDate()).isEqualTo(sleepDate);
        assertThat(sleepLog.getTimeInBedStart()).isEqualTo(request.getTimeInBedStart());
        assertThat(sleepLog.getTimeInBedEnd()).isEqualTo(request.getTimeInBedEnd());
        assertThat(sleepLog.getTotalTimeInBed()).isEqualTo(totalTimeInBed);
        assertThat(sleepLog.getMorningFeeling()).isEqualTo(request.getMorningFeeling());
    }

    // Tests that toResponse correctly maps a SleepLog to a SleepLogResponse.
    @Test
    public void testToResponse() {
        // Arrange
        SleepLog sleepLog = new SleepLog();
        sleepLog.setUserId(1L);
        sleepLog.setSleepDate(LocalDate.of(2023, 10, 15));
        sleepLog.setTimeInBedStart(LocalDateTime.of(2023, 10, 15, 22, 0));
        sleepLog.setTimeInBedEnd(LocalDateTime.of(2023, 10, 16, 6, 0));
        sleepLog.setTotalTimeInBed(480);
        sleepLog.setMorningFeeling(MorningFeeling.GOOD);

        // Act
        SleepLogResponse response = mapper.toResponse(sleepLog);

        // Assert
        assertThat(response.getUserId()).isEqualTo(sleepLog.getUserId());
        assertThat(response.getSleepDate()).isEqualTo(sleepLog.getSleepDate());
        assertThat(response.getTimeInBedStart()).isEqualTo(sleepLog.getTimeInBedStart());
        assertThat(response.getTimeInBedEnd()).isEqualTo(sleepLog.getTimeInBedEnd());
        assertThat(response.getTotalTimeInBed()).isEqualTo(sleepLog.getTotalTimeInBed());
        assertThat(response.getMorningFeeling()).isEqualTo(sleepLog.getMorningFeeling());
    }

    // Tests that toAverageResponseWithEmptyLogs returns a SleepAveragesResponse
    // with default values.
    @Test
    public void testToAverageResponseWithEmptyLogs() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 31);

        // Act
        SleepAveragesResponse response = mapper.toAverageResponseWithEmptyLogs(startDate, endDate);

        // Assert
        assertThat(response.getDateRangeStart()).isEqualTo(startDate);
        assertThat(response.getDateRangeEnd()).isEqualTo(endDate);
        assertThat(response.getAverageTotalTimeInBed()).isEqualTo(0.0);
        assertThat(response.getAverageTimeInBed()).isEqualTo(LocalTime.of(0, 0));
        assertThat(response.getAverageTimeOutOfBed()).isEqualTo(LocalTime.of(0, 0));
        assertThat(response.getMorningFeelingFrequencies()).containsOnly(
                entry(MorningFeeling.BAD, 0),
                entry(MorningFeeling.OK, 0),
                entry(MorningFeeling.GOOD, 0));
    }

    // Tests that toAverageResponse correctly maps provided averages and frequencies
    // to a SleepAveragesResponse.
    @Test
    public void testToAverageResponse() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 31);
        Double averageTotalTimeInBed = 420.5; // 7 hours and 30.5 minutes
        LocalTime averageTimeInBed = LocalTime.of(22, 30);
        LocalTime averageTimeOutOfBed = LocalTime.of(6, 45);
        Map<MorningFeeling, Integer> frequencies = Map.of(
                MorningFeeling.BAD, 2,
                MorningFeeling.OK, 5,
                MorningFeeling.GOOD, 3);

        // Act
        SleepAveragesResponse response = mapper.toAverageResponse(startDate, endDate, averageTotalTimeInBed,
                averageTimeInBed, averageTimeOutOfBed, frequencies);

        // Assert
        assertThat(response.getDateRangeStart()).isEqualTo(startDate);
        assertThat(response.getDateRangeEnd()).isEqualTo(endDate);
        assertThat(response.getAverageTotalTimeInBed()).isEqualTo(averageTotalTimeInBed);
        assertThat(response.getAverageTimeInBed()).isEqualTo(averageTimeInBed);
        assertThat(response.getAverageTimeOutOfBed()).isEqualTo(averageTimeOutOfBed);
        assertThat(response.getMorningFeelingFrequencies()).containsExactlyInAnyOrderEntriesOf(frequencies);
    }
}