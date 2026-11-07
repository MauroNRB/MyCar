package com.mycar.app.models;

public class TripRecord {
    private long id;
    private long vehicleId;
    private String startTime;
    private String endTime;
    private double distanceKm;
    private double avgSpeedKmh;
    private double maxSpeedKmh;

    public TripRecord() {
    }

    public TripRecord(long id, long vehicleId, String startTime, String endTime,
                      double distanceKm, double avgSpeedKmh, double maxSpeedKmh) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distanceKm = distanceKm;
        this.avgSpeedKmh = avgSpeedKmh;
        this.maxSpeedKmh = maxSpeedKmh;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getAvgSpeedKmh() {
        return avgSpeedKmh;
    }

    public void setAvgSpeedKmh(double avgSpeedKmh) {
        this.avgSpeedKmh = avgSpeedKmh;
    }

    public double getMaxSpeedKmh() {
        return maxSpeedKmh;
    }

    public void setMaxSpeedKmh(double maxSpeedKmh) {
        this.maxSpeedKmh = maxSpeedKmh;
    }
}
