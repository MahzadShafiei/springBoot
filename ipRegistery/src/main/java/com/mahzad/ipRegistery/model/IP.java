package com.mahzad.ipRegistery.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="ips")
public class IP {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int  id;

    private String ip;

    private LocalDateTime accessTime;

    private String UUID;


    protected IP() {
    }

    public IP(String ip, LocalDateTime accessTime, String UUID ) {
        this.ip = ip;
        this.accessTime = accessTime;
        this.UUID = UUID;
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public String getUUID() { return UUID;   }
}
