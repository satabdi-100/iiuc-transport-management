package com.iiuc.transport.controller;

import com.iiuc.transport.model.Route;
import com.iiuc.transport.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
public class RouteController {

    @Autowired
    private RouteService routeService;

    // GET all — JS: fetch('http://localhost:8080/api/routes')
    @GetMapping
    public List<Route> getAll() {
        return routeService.getAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        Route route = routeService.getById(id);
        if (route == null) return "Route not found with id: " + id;
        return route;
    }

    // POST add
    @PostMapping
    public Route add(@RequestBody Route route) {
        return routeService.add(route);
    }

    // PUT update
    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @RequestBody Route route) {
        Route updated = routeService.update(id, route);
        if (updated == null) return "Route not found with id: " + id;
        return updated;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean removed = routeService.delete(id);
        if (!removed) return "Route not found with id: " + id;
        return "Route deleted successfully";
    }
}