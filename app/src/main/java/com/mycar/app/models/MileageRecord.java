package com.mycar.app.models;

public class MileageRecord {
    private long id;
    private long vehicleId;
    private double odometer;
    private String dateTimestamp;
    private String notes;
    private double difference;

    public MileageRecord() {
    }

    public MileageRecord(long id, long vehicleId, double odometer, String dateTimestamp, String notes, double difference) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.odometer = odometer;
        this.dateTimestamp = dateTimestamp;
        this.notes = notes;
        this.difference = difference;
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

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public String getDateTimestamp() {
        return dateTimestamp;
    }

    public void setDateTimestamp(String dateTimestamp) {
        this.dateTimestamp = dateTimestamp;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }
}
