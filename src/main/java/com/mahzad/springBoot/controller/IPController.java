package com.mahzad.springBoot.controller;

import com.mahzad.springBoot.service.IPService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ip")
public class IPController {

    private final IPService ipService;
    public IPController(IPService ipService) {
        this.ipService = ipService;
    }

    @GetMapping("/callMe")
    public void callMe(HttpServletRequest request)
    {
        ipService.registerIP(request);
    }
}
