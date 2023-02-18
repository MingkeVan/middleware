package com.mw.middleware.bean;

public class RedisInfo {
    private Long id;
    private String name;
    private String host;
    private Integer port;
    private String password;

    private RedisStatus status;

    // getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RedisStatus getStatus() {
        return status;
    }

    public void setStatus(RedisStatus status) {
        this.status = status;
    }
}
