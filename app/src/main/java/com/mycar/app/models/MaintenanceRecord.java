package com.mycar.app.models;

public class MaintenanceRecord {
    private long id;
    private long vehicleId;
    private String title;
    private String category;
    private String datePerformed;
    private double odometer;
    private double cost;
    private String workshop;
    private String notes;
    private boolean isScheduled;
    private String targetDate;
    private double targetOdometer;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(long id, long vehicleId, String title, String category, String datePerformed,
                             double odometer, double cost, String workshop, String notes,
                             boolean isScheduled, String targetDate, double targetOdometer) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.title = title;
        this.category = category;
        this.datePerformed = datePerformed;
        this.odometer = odometer;
        this.cost = cost;
        this.workshop = workshop;
        this.notes = notes;
        this.isScheduled = isScheduled;
        this.targetDate = targetDate;
        this.targetOdometer = targetOdometer;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatePerformed() {
        return datePerformed;
    }

    public void setDatePerformed(String datePerformed) {
        this.datePerformed = datePerformed;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public double getTargetOdometer() {
        return targetOdometer;
    }

    public void setTargetOdometer(double targetOdometer) {
        this.targetOdometer = targetOdometer;
    }
}
