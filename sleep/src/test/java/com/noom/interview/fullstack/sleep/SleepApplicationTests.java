package com.noom.interview.fullstack.sleep;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.noom.interview.fullstack.sleep.service.SleepLogService;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("unittest")
class SleepApplicationTests {

    @MockBean
    private SleepLogService sleepLogService;

    @Test
    void contextLoadsInJava() {
        verifyNoInteractions(sleepLogService);
    }
}