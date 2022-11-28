package com.example.hm;

import com.example.hm.dao.impl.FlowMatrixImpl;
import com.example.hm.dao.impl.HierariachalFlowImpl;
import com.example.hm.dao.impl.FlowDataImpl;
import com.example.hm.dao.impl.MobilityDaoImpl;
import com.example.hm.pojo.PopulationResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/mobility")
public class Controller {

    @GET
    @Path("/viewMatrixMap")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMatrixMapView(@QueryParam("state") String state,@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate){
        //N1: state="CA", fromDate="12", toDate="17";
        String response=null;
        //N2:
        try{
            response=new FlowMatrixImpl().getFlowMatrix(state,fromDate,toDate);
        }
        //N3:
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/viewByDefault")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PopulationResponse> getDefaultView(@QueryParam("state") String state, @QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate){
        //N1: state='CA',fromDate=13,toDate=17
        List<PopulationResponse> responses=null;
        //N2:
        try{
            responses=new MobilityDaoImpl().viewPopulationTimeSeries(state,fromDate,toDate);
        }//N3
         catch(Exception e){
            e.printStackTrace();
        }
        //N4
        return responses;
    }

    @GET
    @Path("/viewHierariachalFlow")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSunburstView(@QueryParam("state") String state,@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate){
        String response=null;
        try{
            response=new HierariachalFlowImpl().viewHierariachalFlow(state,fromDate,toDate);
            System.out.println(response);
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/viewFlowMap")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlowMapView(@QueryParam("state") String state,@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate){
        String response=null;
        try{
            response=new FlowDataImpl().getFlowConnectors(state,fromDate,toDate);
            System.out.println(response);
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }


}
