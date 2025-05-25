package com.noom.interview.fullstack.sleep.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.noom.interview.fullstack.sleep.dto.SleepAveragesResponse;
import com.noom.interview.fullstack.sleep.dto.SleepLogRequest;
import com.noom.interview.fullstack.sleep.dto.SleepLogResponse;
import com.noom.interview.fullstack.sleep.model.MorningFeeling;
import com.noom.interview.fullstack.sleep.model.SleepLog;

@Component
public class SleepLogMapper {

    public SleepLogRequest toRequest(SleepLog sleepLog) {
        SleepLogRequest sleepLogRequest = new SleepLogRequest();
        sleepLogRequest.setTimeInBedStart(sleepLog.getTimeInBedStart());
        sleepLogRequest.setTimeInBedEnd(sleepLog.getTimeInBedEnd());
        sleepLogRequest.setMorningFeeling(sleepLog.getMorningFeeling());

        return sleepLogRequest;
    }

    public SleepLog toEntity(SleepLogRequest sleepLogRequest, Long userId, LocalDate sleepDate, int totalTimeInBed) {
        SleepLog sleepLog = new SleepLog();
        sleepLog.setUserId(userId);
        sleepLog.setSleepDate(sleepDate);
        sleepLog.setTimeInBedStart(sleepLogRequest.getTimeInBedStart());
        sleepLog.setTimeInBedEnd(sleepLogRequest.getTimeInBedEnd());
        sleepLog.setTotalTimeInBed(totalTimeInBed);
        sleepLog.setMorningFeeling(sleepLogRequest.getMorningFeeling());

        return sleepLog;
    }

    public SleepLogResponse toResponse(SleepLog sleepLog) {
        SleepLogResponse sleepLogResponse = new SleepLogResponse();
        sleepLogResponse.setUserId(sleepLog.getUserId());
        sleepLogResponse.setSleepDate(sleepLog.getSleepDate());
        sleepLogResponse.setTimeInBedStart(sleepLog.getTimeInBedStart());
        sleepLogResponse.setTimeInBedEnd(sleepLog.getTimeInBedEnd());
        sleepLogResponse.setTotalTimeInBed(sleepLog.getTotalTimeInBed());
        sleepLogResponse.setMorningFeeling(sleepLog.getMorningFeeling());

        return sleepLogResponse;
    }

    public SleepAveragesResponse toAverageResponseWithEmptyLogs(LocalDate startDate, LocalDate endDate) {
        SleepAveragesResponse sleepAveragesResponse = new SleepAveragesResponse();
        sleepAveragesResponse.setDateRangeStart(startDate);
        sleepAveragesResponse.setDateRangeEnd(endDate);
        sleepAveragesResponse.setAverageTotalTimeInBed(0.0);
        sleepAveragesResponse.setAverageTimeInBed(LocalTime.of(0, 0));
        sleepAveragesResponse.setAverageTimeOutOfBed(LocalTime.of(0, 0));
        sleepAveragesResponse.setMorningFeelingFrequencies(Arrays.stream(MorningFeeling.values())
                .collect(Collectors.toMap(feeling -> feeling, feeling -> 0)));

        return sleepAveragesResponse;
    }

    public SleepAveragesResponse toAverageResponse(LocalDate startDate, LocalDate endDate, Double averageTotalTimeInBed,
            LocalTime averageTimeInBed, LocalTime averageTimeOutOfBed,
            java.util.Map<MorningFeeling, Integer> morningFeelingFrequencies) {
        SleepAveragesResponse sleepAveragesResponse = new SleepAveragesResponse();
        sleepAveragesResponse.setDateRangeStart(startDate);
        sleepAveragesResponse.setDateRangeEnd(endDate);
        sleepAveragesResponse.setAverageTotalTimeInBed(averageTotalTimeInBed);
        sleepAveragesResponse.setAverageTimeInBed(averageTimeInBed);
        sleepAveragesResponse.setAverageTimeOutOfBed(averageTimeOutOfBed);
        sleepAveragesResponse.setMorningFeelingFrequencies(morningFeelingFrequencies);

        return sleepAveragesResponse;
    }

}
