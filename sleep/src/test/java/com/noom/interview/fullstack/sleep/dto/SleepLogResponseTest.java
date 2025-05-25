package com.noom.interview.fullstack.sleep.dto;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class SleepLogResponseTest {

    @Test
    public void testNoArgsConstructor() {
        // Act
        SleepLogResponse response = new SleepLogResponse();

        // Assert
        assertThat(response.getUserId()).isNull();
        assertThat(response.getSleepDate()).isNull();
        assertThat(response.getTimeInBedStart()).isNull();
        assertThat(response.getTimeInBedEnd()).isNull();
        assertThat(response.getTotalTimeInBed()).isNull();
        assertThat(response.getMorningFeeling()).isNull();
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        Long userId = 1L;
        LocalDate sleepDate = LocalDate.of(2023, 10, 15);
        LocalDateTime start = LocalDateTime.of(2023, 10, 15, 22, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 16, 6, 0);
        Integer totalTime = 480;
        MorningFeeling feeling = MorningFeeling.GOOD;

        // Act
        SleepLogResponse response = new SleepLogResponse(userId, sleepDate, start, end, totalTime, feeling);

        // Assert
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getSleepDate()).isEqualTo(sleepDate);
        assertThat(response.getTimeInBedStart()).isEqualTo(start);
        assertThat(response.getTimeInBedEnd()).isEqualTo(end);
        assertThat(response.getTotalTimeInBed()).isEqualTo(totalTime);
        assertThat(response.getMorningFeeling()).isEqualTo(feeling);
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        SleepLogResponse response = new SleepLogResponse();
        Long userId = 1L;
        LocalDate sleepDate = LocalDate.of(2023, 10, 15);
        LocalDateTime start = LocalDateTime.of(2023, 10, 15, 22, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 16, 6, 0);
        Integer totalTime = 480;
        MorningFeeling feeling = MorningFeeling.GOOD;

        // Act
        response.setUserId(userId);
        response.setSleepDate(sleepDate);
        response.setTimeInBedStart(start);
        response.setTimeInBedEnd(end);
        response.setTotalTimeInBed(totalTime);
        response.setMorningFeeling(feeling);

        // Assert
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getSleepDate()).isEqualTo(sleepDate);
        assertThat(response.getTimeInBedStart()).isEqualTo(start);
        assertThat(response.getTimeInBedEnd()).isEqualTo(end);
        assertThat(response.getTotalTimeInBed()).isEqualTo(totalTime);
        assertThat(response.getMorningFeeling()).isEqualTo(feeling);
    }
}