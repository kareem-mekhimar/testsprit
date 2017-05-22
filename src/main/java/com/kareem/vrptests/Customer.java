/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests;

/**
 *
 * @author km
 */

public class Customer {
  
   private String id ;
   
   private Location location ;
   
   private Integer demand ;

    public Customer() {
    }

    public Customer(String id, Location location, Integer demand) {
        this.id = id;
        this.location = location;
        this.demand = demand;
    }

    public Integer getDemand() {
        return demand;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setDemand(Integer demand) {
        this.demand = demand;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
   
   
}
