/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bridge
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Solution {
  
    private List<Route> routes ;
    
    private List<UnAssignedCustomer> unAssignedCustomers ;

    public Solution() {
    }

    public Solution(List<Route> routes, List<UnAssignedCustomer> assignedCustomers) {
        this.routes = routes;
        this.unAssignedCustomers = assignedCustomers;
    }

    public List<UnAssignedCustomer> getUnAssignedCustomers() {
        return unAssignedCustomers;
    }

    public void setUnAssignedCustomers(List<UnAssignedCustomer> unAssignedCustomers) {
        this.unAssignedCustomers = unAssignedCustomers;
    }



    public List<Route> getRoutes() {
        return routes;
    }


    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    
    
}
