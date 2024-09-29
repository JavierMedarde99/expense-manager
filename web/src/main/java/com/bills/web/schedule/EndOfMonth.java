package com.bills.web.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bills.web.services.ScheduleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EndOfMonth {

    private final ScheduleService scheduleService;

    @Scheduled(cron = "1 0 1 * * *")//TODO: change cron
    public void scheduleNewMonth(){
        scheduleService.endOfMonth();
    }

}
