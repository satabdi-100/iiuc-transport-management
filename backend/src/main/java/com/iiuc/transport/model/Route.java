package com.iiuc.transport.model;

public class Route {

    private Long id;
    private String routeName;
    private String busNumber;

    public Route() {
    }

    public Route(Long id, String routeName, String busNumber) {
        this.id = id;
        this.routeName = routeName;
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

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }
}