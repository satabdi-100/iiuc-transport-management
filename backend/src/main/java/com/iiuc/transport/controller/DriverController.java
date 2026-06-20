package com.iiuc.transport.controller;

import com.iiuc.transport.model.Driver;
import com.iiuc.transport.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    @Autowired
    private DriverService driverService;

    // GET all — JS: fetch('http://localhost:8080/api/drivers')
    @GetMapping
    public List<Driver> getAll() {
        return driverService.getAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        Driver driver = driverService.getById(id);
        if (driver == null) return "Driver not found with id: " + id;
        return driver;
    }

    // POST add
    @PostMapping
    public Driver add(@RequestBody Driver driver) {
        return driverService.add(driver);
    }

    // PUT update
    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @RequestBody Driver driver) {
        Driver updated = driverService.update(id, driver);
        if (updated == null) return "Driver not found with id: " + id;
        return updated;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean removed = driverService.delete(id);
        if (!removed) return "Driver not found with id: " + id;
        return "Driver deleted successfully";
    }
}