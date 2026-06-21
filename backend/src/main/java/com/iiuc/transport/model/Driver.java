package com.iiuc.transport.model;

public class Driver {

    private Long id;
    private String driverName;
    private String busNumber;
    private String mobileNumber;

    public Driver() {
    }

    public Driver(Long id, String driverName, String busNumber) {
        this.id = id;
        this.driverName = driverName;
        this.busNumber = busNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}