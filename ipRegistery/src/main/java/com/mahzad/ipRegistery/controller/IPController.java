package com.mahzad.ipRegistery.controller;

import com.mahzad.ipRegistery.service.IPService;
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
    public String callMe(HttpServletRequest request)
    {
        return ipService.registerIP(request);
    }
}
