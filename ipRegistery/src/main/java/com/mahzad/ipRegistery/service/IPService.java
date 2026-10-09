package com.mahzad.ipRegistery.service;

import com.mahzad.ipRegistery.model.IP;
import com.mahzad.ipRegistery.repository.IPRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Service
public class IPService {

    private final IPRepository ipRepository;
    private final RestClient restClient;
    public IPService(IPRepository ipRepository, RestClient.Builder builder) {
        this.restClient = builder.build();
        this.ipRepository = ipRepository;
    }

    @Transactional
    public String registerIP(HttpServletRequest request)
    {
        var ip = new IP(request.getRemoteAddr(), LocalDateTime.now());

        ipRepository.save(ip);
        return "Done";
        //return restClient.get()
        //       //.uri("http://localhost:8080/users/getUserByIdJpql?id=5")
        //        .uri("http://localhost:8080/users/hello")
        //        .retrieve()
        //        .body(String.class);
    }
}
