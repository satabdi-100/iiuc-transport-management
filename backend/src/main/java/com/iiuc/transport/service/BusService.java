package com.iiuc.transport.service;

import com.iiuc.transport.model.Bus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {

    private List<Bus> busList = new ArrayList<>();

    private Long nextId = 701L;

    public Bus add(Bus bus) {
        bus.setId(nextId++);
        busList.add(bus);
        return bus;
    }

    public List<Bus> getAll() {
        return busList;
    }

    public Bus getById(Long id) {
        for (Bus b : busList) {
            if (b.getId().equals(id))
                return b;
        }
        return null;
    }

    public Bus update(Long id, Bus updated) {
        Bus existing = getById(id);
        if (existing == null)
            return null;
        existing.setBusNumber(updated.getBusNumber());
        existing.setCapacity(updated.getCapacity());
        existing.setSemester(updated.getSemester());
        return existing;
    }

    public boolean delete(Long id) {
        return busList.removeIf(b -> b.getId().equals(id));
    }
}