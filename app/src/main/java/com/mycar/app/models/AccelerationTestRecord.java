package com.mycar.app.models;

public class AccelerationTestRecord {
    private long id;
    private long vehicleId;
    private double elapsedTimeSeconds;
    private String timestamp;
    private double initialSpeedKmh;
    private double finalSpeedKmh;

    public AccelerationTestRecord() {
    }

    public AccelerationTestRecord(long id, long vehicleId, double elapsedTimeSeconds, String timestamp, double initialSpeedKmh, double finalSpeedKmh) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.elapsedTimeSeconds = elapsedTimeSeconds;
        this.timestamp = timestamp;
        this.initialSpeedKmh = initialSpeedKmh;
        this.finalSpeedKmh = finalSpeedKmh;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getElapsedTimeSeconds() {
        return elapsedTimeSeconds;
    }

    public void setElapsedTimeSeconds(double elapsedTimeSeconds) {
        this.elapsedTimeSeconds = elapsedTimeSeconds;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getInitialSpeedKmh() {
        return initialSpeedKmh;
    }

    public void setInitialSpeedKmh(double initialSpeedKmh) {
        this.initialSpeedKmh = initialSpeedKmh;
    }

    public double getFinalSpeedKmh() {
        return finalSpeedKmh;
    }

    public void setFinalSpeedKmh(double finalSpeedKmh) {
        this.finalSpeedKmh = finalSpeedKmh;
    }
}
