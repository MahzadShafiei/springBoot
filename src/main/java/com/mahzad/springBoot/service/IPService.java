package com.mahzad.springBoot.service;

import com.mahzad.springBoot.model.IP;
import com.mahzad.springBoot.repository.IPRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class IPService {

    private final IPRepository ipRepository;
    public IPService(IPRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public void registerIP(HttpServletRequest request)
    {
        var ip = new IP(request.getRemoteAddr(), LocalDateTime.now());
        ipRepository.save(ip);
    }
}
