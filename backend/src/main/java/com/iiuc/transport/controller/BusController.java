package com.iiuc.transport.controller;

import com.iiuc.transport.model.Bus;
import com.iiuc.transport.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@CrossOrigin(origins = "*")   // JS fetch() থেকে access দেওয়ার জন্য
public class BusController {

    @Autowired
    private BusService busService;

    // GET all — JS: fetch('http://localhost:8080/api/buses')
    @GetMapping
    public List<Bus> getAll() {
        return busService.getAll();
    }

    // GET by ID — JS: fetch('http://localhost:8080/api/buses/701')
    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        Bus bus = busService.getById(id);
        if (bus == null) return "Bus not found with id: " + id;
        return bus;
    }

    // POST add — JS: fetch('/api/buses', { method: 'POST', body: JSON... })
    @PostMapping
    public Bus add(@RequestBody Bus bus) {
        return busService.add(bus);
    }

    // PUT update — JS: fetch('/api/buses/701', { method: 'PUT', body: JSON... })
    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @RequestBody Bus bus) {
        Bus updated = busService.update(id, bus);
        if (updated == null) return "Bus not found with id: " + id;
        return updated;
    }

    // DELETE — JS: fetch('/api/buses/701', { method: 'DELETE' })
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean removed = busService.delete(id);
        if (!removed) return "Bus not found with id: " + id;
        return "Bus deleted successfully";
    }
}