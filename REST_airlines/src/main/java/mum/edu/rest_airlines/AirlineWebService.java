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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.stream.JsonParser;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

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
       MemoryDBA.getInstance();
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
//        FlightService flight = new FlightService(new FlightDao());        
//        List<Flight> list = flight.findAll();
//        return JsonHelper.listFlightToJson(list);

        return JsonHelper.listFlightToJson(MemoryDBA.getInstance().getFlightList());
    }
    
    
    @GET
    @Path("/listAirline")
    @Produces(MediaType.APPLICATION_JSON)
    public String allAirLine(){
//        AirlineService airlineService = new AirlineService(new AirlineDao());
//        List<Airline> list = airlineService.findAll();
//        return JsonHelper.listAirlineToJson(list);

        return JsonHelper.listAirlineToJson(MemoryDBA.getInstance().getAirlineList());
    }
    
    @GET
    @Path("/listAirplane")
    @Produces(MediaType.APPLICATION_JSON)
    public String allAirplane(){
//        AirplaneService airlineService = new AirplaneService(new AirplaneDao());
//        List<Airplane> list = airlineService.findAll();
//        return JsonHelper.listAirplaneToJson(list);
        return JsonHelper.listAirplaneToJson(MemoryDBA.getInstance().getAirplaneList());
    }
    
    @GET
    @Path("/listAirport")
    @Produces(MediaType.APPLICATION_JSON)
    public String allAirport(){
        //AirportService airlineService = new AirportService(new AirportDao());
        //List<Airport> list = airlineService.findAll();
        return JsonHelper.listAirportToJson(MemoryDBA.getInstance().getAirportList());
    }

    /**
     * PUT method for updating or creating an instance of AirlineWebService
     * @param content representation for the resource
     */

    
//    @POST
//    @Path("/createAirline")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)  
//    public String createAirline(
//      @FormParam("name") String name,
//      @Context HttpServletResponse servletResponse) throws IOException {
//        
//        Airline a = new Airline(name);
//        a.setId(MemoryDBA.getInstance().getAirlineList().size());
//        MemoryDBA.getInstance().getAirlineList().add(a);
//        
//        return JsonHelper.airlineToJsonString(a);
//    }
    
    @POST
    @Path("/createAirport")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)  
    public String createAirport(        
        @FormParam("name") String name,
        @FormParam("code") String code,
        @FormParam("city") String city,
        @FormParam("country") String country,
        @Context HttpServletResponse servletResponse) throws IOException {
        
        
        Airport airPort = new Airport(code, name, city, country);
        
        
        if(MemoryDBA.getInstance()!=null){
            if(MemoryDBA.getInstance().getAirportList()!=null){
                airPort.setId(MemoryDBA.getInstance().getAirportList().size());
                MemoryDBA.getInstance().getAirportList().add(airPort);
                return JsonHelper.airPortToJsonString(airPort);
            }
            else{
                return "Airport List is null";
            }
        }
        
        return "Databse error";
        
    }
    
    @POST
    @Path("/createAirline")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_JSON)  
    public String createAirline(InputStream inputStreamData) throws IOException {
        
        JsonParser parser = Json.createParser(inputStreamData);
//        String flightInfo = "";
        Airline airline = new Airline();
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch(event) {               
               case KEY_NAME:
               {
                  String key = parser.getString();
                  if(key.equals("name")){
                      parser.next();
                      airline.setId(MemoryDBA.getInstance().getAirlineList().size()+1);
                      airline.setName(parser.getString());
                  }
                  else if(key.equals("flightnr")){
                      parser.next();
                      String flightNumber = parser.getString();
                      List<Flight> l = MemoryDBA.getInstance().getFlightList();
                      for(Flight f:l){
                          if(f.getFlightnr().equals(flightNumber)){
                              airline.addFlight(f);
                              f.setAirline(airline);
                              System.out.println(f.getAirline().getName());
                              break;
                          }
                      }
//                      flightInfo = parser.getString();
                  }
                  break;
               }
            }
        }
        
        
        
        MemoryDBA.getInstance().getAirlineList().add(airline);
        
//        StringBuilder crunchifyBuilder = new StringBuilder();
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(inputStreamData));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                crunchifyBuilder.append(line);
//            }
//        } catch (Exception e) {
//            System.out.println("Error Parsing: - ");
//        }
//        System.out.println("Data Received: " + crunchifyBuilder.toString());

        // return HTTP response 200 in case of success
        return "Update Success";
    }
}
