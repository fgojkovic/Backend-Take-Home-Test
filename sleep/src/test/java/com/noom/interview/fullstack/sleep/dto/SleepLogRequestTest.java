package com.noom.interview.fullstack.sleep.dto;

import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class SleepLogRequestTest {

    @Test
    public void testSettersAndGetters() {
        // Arrange
        SleepLogRequest request = new SleepLogRequest();
        LocalDateTime start = LocalDateTime.of(2023, 10, 15, 22, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 16, 6, 0);
        MorningFeeling feeling = MorningFeeling.GOOD;

        // Act
        request.setTimeInBedStart(start);
        request.setTimeInBedEnd(end);
        request.setMorningFeeling(feeling);

        // Assert
        assertThat(request.getTimeInBedStart()).isEqualTo(start);
        assertThat(request.getTimeInBedEnd()).isEqualTo(end);
        assertThat(request.getMorningFeeling()).isEqualTo(feeling);
    }

    @Test
    public void testNoArgsConstructor() {
        // Act
        SleepLogRequest request = new SleepLogRequest();

        // Assert
        assertThat(request.getTimeInBedStart()).isNull();
        assertThat(request.getTimeInBedEnd()).isNull();
        assertThat(request.getMorningFeeling()).isNull();
    }
}