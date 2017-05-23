/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests.rest;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Job;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.Coordinate;
import com.graphhopper.jsprit.core.util.Solutions;
import com.kareem.vrptests.Customer;
import com.kareem.vrptests.Location;
import com.kareem.vrptests.Route;
import com.kareem.vrptests.Solution;
import com.kareem.vrptests.UnAssignedCustomer;
import com.kareem.vrptests.Vehicle;
import com.kareem.vrptests.Vehicle2;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Polyline;

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
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Problem getJson() {
//        //TODO return proper representation object
//
//        List<Vehicle> vehicles = new ArrayList<>() ;
//        
//        vehicles.add(new Vehicle("1",2, new Location(5.25, 5.35))) ;
//        
//        vehicles.add(new Vehicle("2",26, new Location(5.25, 5.35))) ;
//        
//        List<Customer> customers = new ArrayList<>() ;
//        
//        customers.add(new Customer("22", new Location(5.65, 5456), 3)) ;
//        
//        return new Problem(vehicles, customers);
//    }

    /**
     * PUT method for updating or creating an instance of VrpResource
     * @param content representation for the resource
     */
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Solution solve(Problem problem) {
        
        final int WEIGHT_INDEX = 0;
        
        Map<Integer,List<Vehicle>> capByType = problem.getVehicles().stream().collect(Collectors.groupingBy(Vehicle::getCapacity));
      
        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        
        List<Service> customerServices = problem.getCustomers().stream().map(c -> {
        
            return Service.Builder.newInstance(c.getId())
                    .addSizeDimension(WEIGHT_INDEX, c.getDemand())
                    .setLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(c.getLocation().getLat(),c.getLocation().getLng()))
                    .build() ;
        }).collect(Collectors.toList())  ;
        
        vrpBuilder.addAllJobs(customerServices) ;
        
        capByType.forEach((k,v) -> {
          
           VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType - "+k)
                   .addCapacityDimension(WEIGHT_INDEX, k);
           VehicleType vehicleType = vehicleTypeBuilder.build();
           
           v.stream().forEach(veh -> {
    
               VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vehicle "+veh.getId());
      
               vehicleBuilder.setStartLocation(com.graphhopper.jsprit.core.problem.Location.newInstance(veh.getLocation().getLat(), veh.getLocation().getLng()));
        
               vehicleBuilder.setType(vehicleType);
       
               VehicleImpl vehicle = vehicleBuilder.build();         
               
               vrpBuilder.addVehicle(vehicle) ;
           });
        });
        
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(vrpBuilder.build());
        
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
        
        Collection<VehicleRoute> list = bestSolution.getRoutes() ;
        
        List<Route> routes = new ArrayList<>() ;
        
        list.stream().forEach(r -> {

            Coordinate start = r.getStart().getLocation().getCoordinate() ;
            
            Route route = new Route(r.getVehicle().getId(),new Location(start.getX(),start.getY()) , 
                    new ArrayList<>()) ;

            routes.add(route) ;
            
            r.getActivities().forEach(tour -> {
             
                Coordinate coordinate = tour.getLocation().getCoordinate() ;
                
                route.getActivityLocations().add(new Location(coordinate.getX(),coordinate.getY())) ;
            });
        });     
        
        List<UnAssignedCustomer> unAssignedCustomers = null ;
        
        Collection<Job> unassignedJobs = bestSolution.getUnassignedJobs() ;
        
        if(unassignedJobs != null)
            
              unAssignedCustomers = unassignedJobs
                      .stream()
                      .map(j -> new UnAssignedCustomer(j.getId()))
                      .collect(Collectors.toList()) ;
    
        Solution solution = new Solution(routes,unAssignedCustomers) ;
        
        return solution ;
    }
}
