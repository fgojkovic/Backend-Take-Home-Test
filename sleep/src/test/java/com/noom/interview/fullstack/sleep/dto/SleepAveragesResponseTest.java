package com.noom.interview.fullstack.sleep.dto;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class SleepAveragesResponseTest {

    @Test
    public void testNoArgsConstructor() {
        // Act
        SleepAveragesResponse response = new SleepAveragesResponse();

        // Assert
        assertThat(response.getDateRangeStart()).isNull();
        assertThat(response.getDateRangeEnd()).isNull();
        assertThat(response.getAverageTotalTimeInBed()).isNull();
        assertThat(response.getAverageTimeInBed()).isNull();
        assertThat(response.getAverageTimeOutOfBed()).isNull();
        assertThat(response.getMorningFeelingFrequencies()).isNull();
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        LocalDate start = LocalDate.of(2023, 10, 1);
        LocalDate end = LocalDate.of(2023, 10, 31);
        Double avgTotalTime = 420.5;
        LocalTime avgTimeInBed = LocalTime.of(22, 30);
        LocalTime avgTimeOutOfBed = LocalTime.of(6, 45);
        Map<MorningFeeling, Integer> frequencies = Map.of(
                MorningFeeling.BAD, 2,
                MorningFeeling.OK, 5,
                MorningFeeling.GOOD, 3);

        // Act
        SleepAveragesResponse response = new SleepAveragesResponse(start, end, avgTotalTime, avgTimeInBed,
                avgTimeOutOfBed, frequencies);

        // Assert
        assertThat(response.getDateRangeStart()).isEqualTo(start);
        assertThat(response.getDateRangeEnd()).isEqualTo(end);
        assertThat(response.getAverageTotalTimeInBed()).isEqualTo(avgTotalTime);
        assertThat(response.getAverageTimeInBed()).isEqualTo(avgTimeInBed);
        assertThat(response.getAverageTimeOutOfBed()).isEqualTo(avgTimeOutOfBed);
        assertThat(response.getMorningFeelingFrequencies()).isEqualTo(frequencies);
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        SleepAveragesResponse response = new SleepAveragesResponse();
        LocalDate start = LocalDate.of(2023, 10, 1);
        LocalDate end = LocalDate.of(2023, 10, 31);
        Double avgTotalTime = 420.5;
        LocalTime avgTimeInBed = LocalTime.of(22, 30);
        LocalTime avgTimeOutOfBed = LocalTime.of(6, 45);
        Map<MorningFeeling, Integer> frequencies = Map.of(
                MorningFeeling.BAD, 2,
                MorningFeeling.OK, 5,
                MorningFeeling.GOOD, 3);

        // Act
        response.setDateRangeStart(start);
        response.setDateRangeEnd(end);
        response.setAverageTotalTimeInBed(avgTotalTime);
        response.setAverageTimeInBed(avgTimeInBed);
        response.setAverageTimeOutOfBed(avgTimeOutOfBed);
        response.setMorningFeelingFrequencies(frequencies);

        // Assert
        assertThat(response.getDateRangeStart()).isEqualTo(start);
        assertThat(response.getDateRangeEnd()).isEqualTo(end);
        assertThat(response.getAverageTotalTimeInBed()).isEqualTo(avgTotalTime);
        assertThat(response.getAverageTimeInBed()).isEqualTo(avgTimeInBed);
        assertThat(response.getAverageTimeOutOfBed()).isEqualTo(avgTimeOutOfBed);
        assertThat(response.getMorningFeelingFrequencies()).isEqualTo(frequencies);
    }

    @Test
    public void testEmptyFrequencies() {
        // Arrange
        SleepAveragesResponse response = new SleepAveragesResponse();
        response.setMorningFeelingFrequencies(Map.of());

        // Act & Assert
        assertThat(response.getMorningFeelingFrequencies()).isEmpty();
    }
}