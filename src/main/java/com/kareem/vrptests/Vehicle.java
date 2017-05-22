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

public class Vehicle {
  
   private String id ;
  
   private Integer capacity ;
  
   private Location location ;

   public Vehicle() {
   }

    public Vehicle(String id, Integer capacity, Location location) {
        this.id = id;
        this.capacity = capacity;
        this.location = location;
    }

   
    public String getId() {
        return id;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    
    public Location getLocation() {
        return location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
  
  
   
}
