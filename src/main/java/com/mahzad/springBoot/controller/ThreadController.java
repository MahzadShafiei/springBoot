package com.mahzad.springBoot.controller;

import com.mahzad.springBoot.service.ThreadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threat")
public class ThreadController {

    private final ThreadService threatService;
    public ThreadController(ThreadService threatService) {
        this.threatService = threatService;
    }

    @GetMapping("/getResultInThread")
    public void getResultInThread()
    {
        threatService.getResultInThreat();
    }
}
