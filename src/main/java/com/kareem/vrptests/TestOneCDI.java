/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Coordinate;
import com.graphhopper.jsprit.core.util.Solutions;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author km
 */

@Named
@ViewScoped
public class TestOneCDI implements Serializable{
    
    enum Type{
        
        CUSTOMER , VEHICLE
    }
    
    private MapModel mapModel ;
    
    private List<Customer2> customers = new ArrayList<>();
    
    private List<Vehicle2> vehicles = new ArrayList<>() ;
    
    private double lat,lng ;
    
    private Type currentType = Type.CUSTOMER ;
    
    private String currentId ;
    
    private Integer demand ;
    
    private List<String> colors = Arrays.asList("red","blue","green","black","pink") ;
    
    private String color ;
    
    @PostConstruct
    private void init()
    {
       mapModel = new DefaultMapModel() ;
       
//        LatLng coord1 = new LatLng(30.606222, 32.271126);
//          
//        String con = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() ;
        //Basic marker
        //mapModel.addOverlay();
    }

    public Marker createCustomer(LatLng latLng,String id)
    {
        Marker m = new Marker(latLng, "Customer "+id,id)   ;
                
        return m ;        
    }

    public void addMarker()
    {
       Marker marker = null ;
       
       if(currentType == Type.CUSTOMER)
       {
          marker = createCustomer(new LatLng(lat, lng), currentId) ;
       
          Customer2 customer = new Customer2(marker, demand, currentId) ;
          
          customers.add(customer) ;
       }
       else
       {
          marker = createVehicle(new LatLng(lat, lng), currentId) ;
       
          Vehicle2 v = new Vehicle2(marker, currentId,demand,color);
          
          vehicles.add(v) ;           
       }
       
       mapModel.addOverlay(marker);
       
       currentId = null ;
       
       demand = null ;
       
       color = null ;
       
    }
    
    public double getLat() {
        return lat;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    
    public double getLng() {
        return lng;
    }

    public List<String> getColors() {
        return colors;
    }

    
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
    
    
    public Marker createVehicle(LatLng latLng,String id)
    { 
        Marker m = new Marker(latLng, "Vechile "+id,
                id,"https://avionicus.com/plugins/avtrack/templates/skin/default/images/ic_tr_run.png")   ;
        
        return m ;
    }
    
    public MapModel getMapModel() {
        return mapModel;
    }
    
    public Type[] getTypes()
    {
       return Type.values() ;
    }

    public String getCurrentId() {
        return currentId;
    }

    public Type getCurrentType() {
        return currentType;
    }

    public Integer getDemand() {
        return demand;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public void setCurrentType(Type currentType) {
        this.currentType = currentType;
    }

    public void setDemand(Integer demand) {
        this.demand = demand;
    }
    
    public void solve()
    { 
        final int WEIGHT_INDEX = 0;
      
        Map<Integer,List<Vehicle2>> capByType = vehicles.stream().collect(Collectors.groupingBy(Vehicle2::getCapacity));
      
        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        
        List<Service> customerServices = this.customers.stream().map(c -> {
        
            return Service.Builder.newInstance(c.getId())
                    .addSizeDimension(WEIGHT_INDEX, c.getDemand())
                    .setLocation(Location.newInstance(c.getMarker().getLatlng().getLat(),c.getMarker().getLatlng().getLng() ))
                    .build() ;
        }).collect(Collectors.toList())  ;
        
        vrpBuilder.addAllJobs(customerServices) ;
        
        capByType.forEach((k,v) -> {
          
           VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType - "+k)
                   .addCapacityDimension(WEIGHT_INDEX, k);
           VehicleType vehicleType = vehicleTypeBuilder.build();
           
           v.stream().forEach(veh -> {
    
               VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vehicle "+veh.getId());
      
               vehicleBuilder.setStartLocation(Location.newInstance(veh.getMarker().getLatlng().getLat(), veh.getMarker().getLatlng().getLng()));
        
               vehicleBuilder.setType(vehicleType);
       
               VehicleImpl vehicle = vehicleBuilder.build();         
               
               vrpBuilder.addVehicle(vehicle) ;
           });
        });
        
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(vrpBuilder.build());
        
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
        
        Collection<VehicleRoute> list = bestSolution.getRoutes() ;
        
        //System.out.println("com.kareem.vresadfdsfsdfsdfsdfsdfdsfptests.TestOneCDI.solve()"+list);
        
        list.stream().forEach(r -> {

            Polyline polyline = new Polyline() ;
            
            Coordinate start = r.getStart().getLocation().getCoordinate() ;
            
            polyline.getPaths().add(new LatLng(start.getX(), start.getY())) ;
            
            r.getActivities().forEach(tour -> {
            
                System.out.println("com.kareem.vrptests.TestOneCDI.solve()"+tour);
                
                Coordinate coordinate = tour.getLocation().getCoordinate() ;
                
                polyline.getPaths().add(new LatLng(coordinate.getX(), coordinate.getY())) ;
                
                polyline.setStrokeColor("#FF9900");
                
                polyline.setStrokeOpacity(1);
                
                polyline.setStrokeWeight(10);
                
                mapModel.addOverlay(polyline);
            });
        });
    }
    
    public String doTest()
    {
        final int WEIGHT_INDEX = 0;
        VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType")
                .addCapacityDimension(WEIGHT_INDEX, 2);
        VehicleType vehicleType = vehicleTypeBuilder.build();


        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vehicle");
        vehicleBuilder.setStartLocation(Location.newInstance(10, 10));
        vehicleBuilder.setType(vehicleType);
 
        VehicleImpl vehicle = vehicleBuilder.build();

		/*
         * build services at the required locations, each with a capacity-demand of 1.
		 */
        Service service1 = Service.Builder.newInstance("1").addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(5, 7)).build();
        Service service2 = Service.Builder.newInstance("2").addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(5, 13)).build();

        Service service3 = Service.Builder.newInstance("3").addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(15, 7)).build();
        Service service4 = Service.Builder.newInstance("4").addSizeDimension(WEIGHT_INDEX, 1).setLocation(Location.newInstance(15, 13)).build();


        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        vrpBuilder.addVehicle(vehicle);
        vrpBuilder.addJob(service1).addJob(service2).addJob(service3).addJob(service4);
        
        
        VehicleRoutingProblem problem = vrpBuilder.build();

		/* 
         * get the algorithm out-of-the-box.
		 */
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);

		/*
         * and search a solution
		 */
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();

		/*
         * get the best
		 */
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);

        //bestSolution.getRoutes().stream().forEach(System.out::println);
        
       // new VrpXMLWriter(problem, solutions).write("e:/output/problem-with-solution.xml");

        SolutionPrinter.print(new PrintWriter(System.out), problem, bestSolution, SolutionPrinter.Print.VERBOSE) ;

      
        return bestSolution.getRoutes().toString() ;
		/*
         * plot
		 */
        //new Plotter(problem,bestSolution).plot("e:/output/plot.png","simple example");

        /*
        render problem and solution with GraphStream
         */
        //new GraphStreamViewer(problem, bestSolution).labelWith(Label.ID).setRenderDelay(200).display();  
    }    
}
