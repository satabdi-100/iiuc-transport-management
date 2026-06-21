package com.iiuc.transport.service;

import com.iiuc.transport.model.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private List<Route> routeList = new ArrayList<>();

    private Long nextId = 801L;

    public Route add(Route route) {
        route.setId(nextId++);
        routeList.add(route);
        return route;
    }

    public List<Route> getAll() {
        return routeList;
    }

    public Route getById(Long id) {
        for (Route r : routeList) {
            if (r.getId().equals(id))
                return r;
        }
        return null;
    }

    public Route update(Long id, Route updated) {
        Route existing = getById(id);
        if (existing == null)
            return null;
        existing.setRouteName(updated.getRouteName());
        existing.setBusNumber(updated.getBusNumber());
        return existing;
    }

    public boolean delete(Long id) {
        return routeList.removeIf(r -> r.getId().equals(id));
    }
}