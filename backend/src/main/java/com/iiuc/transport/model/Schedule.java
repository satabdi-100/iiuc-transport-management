package com.iiuc.transport.model;

public class Schedule {

    private Long id;
    private String routeName;
    private String schedule;
    private String driverName;
    private String busNumber;

    public Schedule() {
    }

    public Schedule(Long id, String routeName, String schedule,
            String driverName, String busNumber) {
        this.id = id;
        this.routeName = routeName;
        this.schedule = schedule;
        this.driverName = driverName;
        this.busNumber = busNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
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
}