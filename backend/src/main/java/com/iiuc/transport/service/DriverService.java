package com.iiuc.transport.service;

import com.iiuc.transport.model.Driver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    private List<Driver> driverList = new ArrayList<>();

    private Long nextId = 1001L;

    public Driver add(Driver driver) {
        driver.setId(nextId++);
        driverList.add(driver);
        return driver;
    }

    public List<Driver> getAll() {
        return driverList;
    }

    public Driver getById(Long id) {
        for (Driver d : driverList) {
            if (d.getId().equals(id))
                return d;
        }
        return null;
    }

    public Driver update(Long id, Driver updated) {
        Driver existing = getById(id);
        if (existing == null)
            return null;
        existing.setDriverName(updated.getDriverName());
        existing.setBusNumber(updated.getBusNumber());
        return existing;
    }

    public boolean delete(Long id) {
        return driverList.removeIf(d -> d.getId().equals(id));
    }
}