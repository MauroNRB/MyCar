package com.mycar.app.models;

public class Vehicle {
    private long id;
    private String name;
    private String manufacturer;
    private String model;
    private int yearFab;
    private int yearModel;
    private String plate;
    private String fuelType;
    private double currentOdometer;
    private String engineInfo;

    public Vehicle() {
    }

    public Vehicle(long id, String name, String manufacturer, String model, int yearFab,
                   int yearModel, String plate, String fuelType, double currentOdometer, String engineInfo) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.yearFab = yearFab;
        this.yearModel = yearModel;
        this.plate = plate;
        this.fuelType = fuelType;
        this.currentOdometer = currentOdometer;
        this.engineInfo = engineInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearFab() {
        return yearFab;
    }

    public void setYearFab(int yearFab) {
        this.yearFab = yearFab;
    }

    public int getYearModel() {
        return yearModel;
    }

    public void setYearModel(int yearModel) {
        this.yearModel = yearModel;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getCurrentOdometer() {
        return currentOdometer;
    }

    public void setCurrentOdometer(double currentOdometer) {
        this.currentOdometer = currentOdometer;
    }

    public String getEngineInfo() {
        return engineInfo;
    }

    public void setEngineInfo(String engineInfo) {
        this.engineInfo = engineInfo;
    }
}
