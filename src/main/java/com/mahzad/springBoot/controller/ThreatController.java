package com.mahzad.springBoot.controller;

import com.mahzad.springBoot.service.ThreatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threat")
public class ThreatController {

    private final ThreatService threatService;
    public ThreatController(ThreatService threatService) {
        this.threatService = threatService;
    }

    @GetMapping("/getResultInThread")
    public void getResultInThread()
    {
        threatService.getResultInThreat();
    }
}
