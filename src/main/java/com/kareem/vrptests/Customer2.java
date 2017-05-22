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

public class Customer2 {
   
    private Marker marker ;
    
    private int demand ;

    private String id ;

    public Customer2(Marker marker, int demand, String id) {
        this.marker = marker;
        this.demand = demand;
        this.id = id;
    }
   
    
    public int getDemand() {
        return demand;
    }

    public Marker getMarker() {
        return marker;
    }

    public String getId() {
        return id;
    }

    
    
}
