package io.lacrobate.tiago.controller;

import io.lacrobate.tiago.adapter.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @GetMapping("/add-birthday")
    public String addBirthdayEvent() {
        log.info("Adding birthday event...");
        return googleCalendarService.createBirthdayEvent();
    }
}