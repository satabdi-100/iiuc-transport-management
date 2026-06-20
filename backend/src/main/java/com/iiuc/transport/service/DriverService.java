package com.iiuc.transport.service;

import com.iiuc.transport.model.Driver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    // In-memory storage — matches JS: let drivers = []
    private List<Driver> driverList = new ArrayList<>();

    // ID generator — matches JS: let driverNextId = 1001
    private Long nextId = 1001L;

    // ADD — matches JS: drivers.push(...)
    public Driver add(Driver driver) {
        driver.setId(nextId++);
        driverList.add(driver);
        return driver;
    }

    // GET ALL — matches JS: drivers
    public List<Driver> getAll() {
        return driverList;
    }

    // GET BY ID — matches JS: drivers.find(d => d.id === id)
    public Driver getById(Long id) {
        for (Driver d : driverList) {
            if (d.getId().equals(id)) return d;
        }
        return null;
    }

    // UPDATE — matches JS: d.driverName = ..., d.busNumber = ...
    public Driver update(Long id, Driver updated) {
        Driver existing = getById(id);
        if (existing == null) return null;
        existing.setDriverName(updated.getDriverName());
        existing.setBusNumber(updated.getBusNumber());
        return existing;
    }

    // DELETE — matches JS: drivers = drivers.filter(d => d.id !== id)
    public boolean delete(Long id) {
        return driverList.removeIf(d -> d.getId().equals(id));
    }
}