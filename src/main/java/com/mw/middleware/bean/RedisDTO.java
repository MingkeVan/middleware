package com.mw.middleware.bean;

import java.util.Objects;

public class RedisDTO {

    // name
    String name;

    // host
    String host;

    // port
    String port;

    // username
    String username;

    // password
    String password;

    String status;

    public RedisDTO() {
    }

    public RedisDTO(RedisPojo redisPojo) {
        this.name = redisPojo.getName();
        this.host = redisPojo.getHost();
        this.port = String.valueOf(redisPojo.getPort());
        this.username = redisPojo.getUsername();
        this.password = redisPojo.getPasswd();
        this.status = "unknown";
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RedisDTO{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }

    // override equals and hashCode method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedisDTO redisDTO = (RedisDTO) o;

        return Objects.equals(name, redisDTO.name) &&
                Objects.equals(host, redisDTO.host) &&
                Objects.equals(port, redisDTO.port);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }


}
