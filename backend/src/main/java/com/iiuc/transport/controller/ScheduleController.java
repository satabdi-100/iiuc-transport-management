package com.iiuc.transport.controller;

import com.iiuc.transport.model.Schedule;
import com.iiuc.transport.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<Schedule> getAll() {
        return scheduleService.getAll();
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getById(id);
        if (schedule == null)
            return "Schedule not found with id: " + id;
        return schedule;
    }

    @PostMapping
    public Schedule add(@RequestBody Schedule schedule) {
        return scheduleService.add(schedule);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @RequestBody Schedule schedule) {
        Schedule updated = scheduleService.update(id, schedule);
        if (updated == null)
            return "Schedule not found with id: " + id;
        return updated;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean removed = scheduleService.delete(id);
        if (!removed)
            return "Schedule not found with id: " + id;
        return "Schedule deleted successfully";
    }
}