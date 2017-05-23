/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests;

/**
 *
 * @author Bridge
 */

public class UnAssignedCustomer {
   
  private String customerId ;
  
  private String reason = "Greater Than Vehcile Capacity" ;

    public UnAssignedCustomer() {
    }

    public UnAssignedCustomer(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getReason() {
        return reason;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
  
  
}
