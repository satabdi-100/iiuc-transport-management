package com.iiuc.transport.controller;

import com.iiuc.transport.model.Bus;
import com.iiuc.transport.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@CrossOrigin(origins = "*")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping
    public List<Bus> getAll() {
        return busService.getAll();
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        Bus bus = busService.getById(id);
        if (bus == null)
            return "Bus not found with id: " + id;
        return bus;
    }

    @PostMapping
    public Bus add(@RequestBody Bus bus) {
        return busService.add(bus);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @RequestBody Bus bus) {
        Bus updated = busService.update(id, bus);
        if (updated == null)
            return "Bus not found with id: " + id;
        return updated;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean removed = busService.delete(id);
        if (!removed)
            return "Bus not found with id: " + id;
        return "Bus deleted successfully";
    }
}