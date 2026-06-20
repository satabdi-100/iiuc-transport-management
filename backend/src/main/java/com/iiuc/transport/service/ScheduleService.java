package com.iiuc.transport.service;

import com.iiuc.transport.model.Schedule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    // In-memory storage — matches JS: let schedules = []
    private List<Schedule> scheduleList = new ArrayList<>();

    // ID generator — matches JS: let scheduleNextId = 901
    private Long nextId = 901L;

    // ADD — matches JS: schedules.push(...)
    public Schedule add(Schedule schedule) {
        schedule.setId(nextId++);
        scheduleList.add(schedule);
        return schedule;
    }

    // GET ALL — matches JS: schedules
    public List<Schedule> getAll() {
        return scheduleList;
    }

    // GET BY ID — matches JS: schedules.find(s => s.id === id)
    public Schedule getById(Long id) {
        for (Schedule s : scheduleList) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    // UPDATE — matches JS: s.routeName = ..., s.schedule = ...
    public Schedule update(Long id, Schedule updated) {
        Schedule existing = getById(id);
        if (existing == null) return null;
        existing.setRouteName(updated.getRouteName());
        existing.setSchedule(updated.getSchedule());
        existing.setDriverName(updated.getDriverName());
        existing.setBusNumber(updated.getBusNumber());
        return existing;
    }

    // DELETE — matches JS: schedules = schedules.filter(s => s.id !== id)
    public boolean delete(Long id) {
        return scheduleList.removeIf(s -> s.getId().equals(id));
    }
}