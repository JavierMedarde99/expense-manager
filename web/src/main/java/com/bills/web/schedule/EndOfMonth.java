package com.bills.web.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EndOfMonth {
    @Scheduled(cron = "1 0 1 * *")//TODO: change cron
    public void scheduleNewMonth(){
        
    }
}
