package com.iiuc.transport.service;

import com.iiuc.transport.model.Bus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {

    // In-memory storage — matches JS: let buses = []
    private List<Bus> busList = new ArrayList<>();

    // ID generator — matches JS: let busNextId = 701
    private Long nextId = 701L;

    // ADD — matches JS: buses.push(...)
    public Bus add(Bus bus) {
        bus.setId(nextId++);
        busList.add(bus);
        return bus;
    }

    // GET ALL — matches JS: buses (the full array)
    public List<Bus> getAll() {
        return busList;
    }

    // GET BY ID — matches JS: buses.find(b => b.id === id)
    public Bus getById(Long id) {
        for (Bus b : busList) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }

    // UPDATE — matches JS: bus.busNumber = ..., bus.capacity = ...
    public Bus update(Long id, Bus updated) {
        Bus existing = getById(id);
        if (existing == null) return null;
        existing.setBusNumber(updated.getBusNumber());
        existing.setCapacity(updated.getCapacity());
        existing.setSemester(updated.getSemester());
        return existing;
    }

    // DELETE — matches JS: buses = buses.filter(b => b.id !== id)
    public boolean delete(Long id) {
        return busList.removeIf(b -> b.getId().equals(id));
    }
}