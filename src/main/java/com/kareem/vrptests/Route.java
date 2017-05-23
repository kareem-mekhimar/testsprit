/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests;

import java.util.List;

/**
 *
 * @author Bridge
 */
public class Route {
    
    private String vehicleId ;
    
    private Location start ;
    
    private List<Location> activityLocations ;

    public Route() {
    }

    public Route(String vehicleId, Location start, List<Location> activityLocations) {
        this.vehicleId = vehicleId;
        this.start = start;
        this.activityLocations = activityLocations;
    }

    public List<Location> getActivityLocations() {
        return activityLocations;
    }

    public Location getStart() {
        return start;
    }

    public void setActivityLocations(List<Location> activityLocations) {
        this.activityLocations = activityLocations;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    
}
