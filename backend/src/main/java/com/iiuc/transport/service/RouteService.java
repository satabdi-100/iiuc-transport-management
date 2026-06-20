package com.iiuc.transport.service;

import com.iiuc.transport.model.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    // In-memory storage — matches JS: let routes = []
    private List<Route> routeList = new ArrayList<>();

    // ID generator — matches JS: let routeNextId = 801
    private Long nextId = 801L;

    // ADD — matches JS: routes.push(...)
    public Route add(Route route) {
        route.setId(nextId++);
        routeList.add(route);
        return route;
    }

    // GET ALL — matches JS: routes
    public List<Route> getAll() {
        return routeList;
    }

    // GET BY ID — matches JS: routes.find(r => r.id === id)
    public Route getById(Long id) {
        for (Route r : routeList) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }

    // UPDATE — matches JS: route.routeName = ..., route.busNumber = ...
    public Route update(Long id, Route updated) {
        Route existing = getById(id);
        if (existing == null) return null;
        existing.setRouteName(updated.getRouteName());
        existing.setBusNumber(updated.getBusNumber());
        return existing;
    }

    // DELETE — matches JS: routes = routes.filter(r => r.id !== id)
    public boolean delete(Long id) {
        return routeList.removeIf(r -> r.getId().equals(id));
    }
}