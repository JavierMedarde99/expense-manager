package com.bills.web.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bills.web.services.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class EndOfMonth {

    private final ScheduleService scheduleService;

    @Scheduled(cron = "1 0 1 * * *")
    public void scheduleNewMonth(){
        log.info("scheduleNewMonth call");
        scheduleService.endOfMonth();
    }

}
