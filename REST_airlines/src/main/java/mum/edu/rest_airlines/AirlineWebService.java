/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mum.edu.rest_airlines;

import com.google.gson.Gson;

import cs545.airline.dao.AirlineDao;
import cs545.airline.dao.AirplaneDao;
import cs545.airline.dao.AirportDao;
import cs545.airline.dao.FlightDao;
import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.nonmanaged.JpaUtil;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;
import cs545.airline.service.FlightService;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;

/**
 * REST Web Service
 *
 * @author Son Vu
 */
@Path("airline")
public class AirlineWebService {
    
//    private AirlineService airlineService = new AirlineService(new AirlineDao());
    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AirlineWebService
     */
    public AirlineWebService() {
       
    }

//    @Override
//    protected void finalize() throws Throwable {
//        JpaUtil.destroyJpaUtil();
//        super.finalize(); //To change body of generated methods, choose Tools | Templates.
//        
//    }
    
    

    /**
     * Retrieves representation of an instance of mum.edu.rest_airlines.AirlineWebService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "AIRLINE REST SERVICE";
    }
    
    @GET
    @Path("/listFlight")
    @Produces(MediaType.APPLICATION_JSON)
    public String allFlight(){
        FlightService flight = new FlightService(new FlightDao());        
        List<Flight> list = flight.findAll();
        return JsonHelper.listFlightToJson(list);
    }
    
    
    @GET
    @Path("/listAirline")
    @Produces(MediaType.APPLICATION_JSON)
    public String allAirLine(){
        AirlineService airlineService = new AirlineService(new AirlineDao());
        List<Airline> list = airlineService.findAll();
        return JsonHelper.listAirlineToJson(list);
    }
    
    @GET
    @Path("/listAirplane")
    @Produces(MediaType.APPLICATION_JSON)
    public String allAirplane(){
        AirplaneService airlineService = new AirplaneService(new AirplaneDao());
        List<Airplane> list = airlineService.findAll();
        return JsonHelper.listAirplaneToJson(list);
    }
    
    @GET
    @Path("/listAirport")
    @Produces(MediaType.APPLICATION_JSON)
    public String allAirport(){
        AirportService airlineService = new AirportService(new AirportDao());
        List<Airport> list = airlineService.findAll();
        return JsonHelper.listAirportToJson(list);
    }

    /**
     * PUT method for updating or creating an instance of AirlineWebService
     * @param content representation for the resource
     */

    
    @POST
    @Path("/createAirline")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)  
    public String createAirline(@FormParam("id") int id,
      @FormParam("name") String name,
      @Context HttpServletResponse servletResponse) throws IOException {
        System.out.println("input content");
//        AirlineService airlineService = new AirlineService(new AirlineDao());
//        airlineService.create(airport);
        return "Finish POST";
    }
    
    @POST
    @Path("/createAirport")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)  
    public String createAirport(
        @FormParam("id") String id,
        @FormParam("name") String name,
        @FormParam("code") String code,
        @FormParam("city") String city,
        @FormParam("country") String country,
        @Context HttpServletResponse servletResponse) throws IOException {
        
        AirportService airportService = new AirportService(new AirportDao());
        Airport airPort = new Airport(code, name, city, country);
        airPort.setId(Long.parseLong(id));
        airportService.create(airPort);
        
        return JsonHelper.airPortToJsonString(airPort);
    }
}
