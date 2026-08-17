package com.mahzad.springBoot.model;

import jakarta.persistence.*;
import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="ips")
public class IP {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int  id;

    private String ip;

    private LocalDateTime accessTime;

    public IP(String ip, LocalDateTime accessTime ) {
        this.ip = ip;
        this.accessTime = accessTime;
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }
}
