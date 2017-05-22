/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests.rest;

import com.kareem.vrptests.Customer;
import com.kareem.vrptests.Customer2;
import com.kareem.vrptests.Vehicle;
import com.kareem.vrptests.Vehicle2;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author km
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Problem {
    
    private List<Vehicle> vehicles ;
    
    private List<Customer> customers ;

    public Problem() {
    }

    public Problem(List<Vehicle> vehicles, List<Customer> customers) {
        this.vehicles = vehicles;
        this.customers = customers;
    }

    
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    
    
    
}
