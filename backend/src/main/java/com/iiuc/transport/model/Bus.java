package com.iiuc.transport.model;

public class Bus {

    private Long id;
    private String busNumber;   // matches JS: busNumber
    private Integer capacity;   // matches JS: capacity
    private String semester;    // matches JS: semester

    public Bus() {}

    public Bus(Long id, String busNumber, Integer capacity, String semester) {
        this.id        = id;
        this.busNumber = busNumber;
        this.capacity  = capacity;
        this.semester  = semester;
    }

    public Long getId()                  { return id; }
    public void setId(Long id)           { this.id = id; }

    public String getBusNumber()               { return busNumber; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }

    public Integer getCapacity()               { return capacity; }
    public void setCapacity(Integer capacity)  { this.capacity = capacity; }

    public String getSemester()                { return semester; }
    public void setSemester(String semester)   { this.semester = semester; }
}