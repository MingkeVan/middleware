package com.mw.middleware.bean;

public class RedisStatus {

    private String version;
    private long uptimeInSeconds;
    private long connectedClients;
    private long blockedClients;
    private long usedMemory;
    private long usedMemoryRss;
    private double memFragmentationRatio;
    private long keyspaceHits;
    private long keyspaceMisses;

    // keyspace hit rate
    private double keyspaceHitRate;

    // instantaneous ops per second
    private long instantaneousOpsPerSec;



    private long totalConnectionsReceived;

    //rejected connections
    private long rejectedConnections;

    //expired keys
    private long expiredKeys;

    //evicted keys
    private long evictedKeys;

    //used cpu sys
    private double usedCpuSys;

    //used cpu user
    private double usedCpuUser;

    //used cpu sys children
    private double usedCpuSysChildren;

    //used cpu user children
    private double usedCpuUserChildren;

    private long totalCommandsProcessed;
    private String role;
    private String clusterEnabled;

    // getters and setters

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getUptimeInSeconds() {
        return uptimeInSeconds;
    }

    public void setUptimeInSeconds(long uptimeInSeconds) {
        this.uptimeInSeconds = uptimeInSeconds;
    }

    public long getConnectedClients() {
        return connectedClients;
    }

    public void setConnectedClients(long connectedClients) {
        this.connectedClients = connectedClients;
    }

    public long getBlockedClients() {
        return blockedClients;
    }

    public void setBlockedClients(long blockedClients) {
        this.blockedClients = blockedClients;
    }

    public long getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public long getUsedMemoryRss() {
        return usedMemoryRss;
    }

    public void setUsedMemoryRss(long usedMemoryRss) {
        this.usedMemoryRss = usedMemoryRss;
    }

    public double getMemFragmentationRatio() {
        return memFragmentationRatio;
    }

    public void setMemFragmentationRatio(double memFragmentationRatio) {
        this.memFragmentationRatio = memFragmentationRatio;
    }

    public long getKeyspaceHits() {
        return keyspaceHits;
    }

    public void setKeyspaceHits(long keyspaceHits) {
        this.keyspaceHits = keyspaceHits;
    }

    public long getKeyspaceMisses() {
        return keyspaceMisses;
    }

    public void setKeyspaceMisses(long keyspaceMisses) {
        this.keyspaceMisses = keyspaceMisses;
    }

    public long getTotalConnectionsReceived() {
        return totalConnectionsReceived;
    }

    public void setTotalConnectionsReceived(long totalConnectionsReceived) {
        this.totalConnectionsReceived = totalConnectionsReceived;
    }

    public long getTotalCommandsProcessed() {
        return totalCommandsProcessed;
    }

    public void setTotalCommandsProcessed(long totalCommandsProcessed) {
        this.totalCommandsProcessed = totalCommandsProcessed;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClusterEnabled() {
        return clusterEnabled;
    }

    public void setClusterEnabled(String clusterEnabled) {
        this.clusterEnabled = clusterEnabled;
    }

    public double getKeyspaceHitRate() {
        return keyspaceHitRate;
    }

    public void setKeyspaceHitRate(double keyspaceHitRate) {
        this.keyspaceHitRate = keyspaceHitRate;
    }

    public long getInstantaneousOpsPerSec() {
        return instantaneousOpsPerSec;
    }

    public void setInstantaneousOpsPerSec(long instantaneousOpsPerSec) {
        this.instantaneousOpsPerSec = instantaneousOpsPerSec;
    }

    public long getRejectedConnections() {
        return rejectedConnections;
    }

    public void setRejectedConnections(long rejectedConnections) {
        this.rejectedConnections = rejectedConnections;
    }

    public long getExpiredKeys() {
        return expiredKeys;
    }

    public void setExpiredKeys(long expiredKeys) {
        this.expiredKeys = expiredKeys;
    }

    public long getEvictedKeys() {
        return evictedKeys;
    }

    public void setEvictedKeys(long evictedKeys) {
        this.evictedKeys = evictedKeys;
    }

    public double getUsedCpuSys() {
        return usedCpuSys;
    }

    public void setUsedCpuSys(double usedCpuSys) {
        this.usedCpuSys = usedCpuSys;
    }

    public double getUsedCpuUser() {
        return usedCpuUser;
    }

    public void setUsedCpuUser(double usedCpuUser) {
        this.usedCpuUser = usedCpuUser;
    }

    public double getUsedCpuSysChildren() {
        return usedCpuSysChildren;
    }

    public void setUsedCpuSysChildren(double usedCpuSysChildren) {
        this.usedCpuSysChildren = usedCpuSysChildren;
    }

    public double getUsedCpuUserChildren() {
        return usedCpuUserChildren;
    }

    public void setUsedCpuUserChildren(double usedCpuUserChildren) {
        this.usedCpuUserChildren = usedCpuUserChildren;
    }
}

