/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests.rest;

import com.kareem.vrptests.Customer;
import com.kareem.vrptests.Location;
import com.kareem.vrptests.Vehicle;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author km
 */
@Path("vrp")
public class VrpResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VrpResource
     */
    public VrpResource() {
    }

    /**
     * Retrieves representation of an instance of com.kareem.vrptests.rest.VrpResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Problem getJson() {
        //TODO return proper representation object

        List<Vehicle> vehicles = new ArrayList<>() ;
        
        vehicles.add(new Vehicle("1",2, new Location(5.25, 5.35))) ;
        
        vehicles.add(new Vehicle("2",26, new Location(5.25, 5.35))) ;
        
        List<Customer> customers = new ArrayList<>() ;
        
        customers.add(new Customer("22", new Location(5.65, 5456), 3)) ;
        
        return new Problem(vehicles, customers);
    }

    /**
     * PUT method for updating or creating an instance of VrpResource
     * @param content representation for the resource
     */
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response solve(Problem problem) {
        
        return null ;
    }
}
