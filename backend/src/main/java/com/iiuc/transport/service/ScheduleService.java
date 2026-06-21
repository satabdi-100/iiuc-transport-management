package com.iiuc.transport.service;

import com.iiuc.transport.model.Schedule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private List<Schedule> scheduleList = new ArrayList<>();

    private Long nextId = 901L;

    public Schedule add(Schedule schedule) {
        schedule.setId(nextId++);
        scheduleList.add(schedule);
        return schedule;
    }

    public List<Schedule> getAll() {
        return scheduleList;
    }

    public Schedule getById(Long id) {
        for (Schedule s : scheduleList) {
            if (s.getId().equals(id))
                return s;
        }
        return null;
    }

    public Schedule update(Long id, Schedule updated) {
        Schedule existing = getById(id);
        if (existing == null)
            return null;
        existing.setRouteName(updated.getRouteName());
        existing.setSchedule(updated.getSchedule());
        existing.setDriverName(updated.getDriverName());
        existing.setBusNumber(updated.getBusNumber());
        return existing;
    }

    public boolean delete(Long id) {
        return scheduleList.removeIf(s -> s.getId().equals(id));
    }
}