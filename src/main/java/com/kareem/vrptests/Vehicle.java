/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests;

import org.primefaces.model.map.Marker;

/**
 *
 * @author km
 */

public class Vehicle {
   
    private Marker marker ;
    
    private String id ;
    
    private int capacity ;

    private String color ;
    
    public Vehicle(Marker marker, String id, int capacity,String color) {
        this.marker = marker;
        this.id = id;
        this.capacity = capacity;
        this.color = color ;
    }

    public String getColor() {
        return color;
    }

    
    public int getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }

    public Marker getMarker() {
        return marker;
    }
    
    
}
