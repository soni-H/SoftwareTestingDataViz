package com.example.hm.utils;

import com.example.hm.pojo.SunburstResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SunburstBuilder {

    public void fetchSunburstResponse(){
        Map<String, SunburstResponse> map=new HashMap();
        try (Session session = HibernateSessionUtil.getSession()) {
            List<Object[]> queryResponses=session.createQuery("select g.locationName,g2.locationName as State,sum(w.populationFlow) from Gazetteer g \n" +
                    "inner join WeeklyFlow w on g.geoid=w.geoidDestination inner join Gazetteer g2 on \n" +
                    "substring(w.geoidDestination,1,2) =cast(g2.geoid as string )\n" +
                    "where w.geoidDestination in (4013,6001,6037,6059,6065,6067,6071,6073,6085,12011,12057,12086,\n" +
                    "12095,12099,15003,17031,26163,32003,\n" +
                    "36047,36059,36081,36103,39049,42101,48029,48113,48201,48439,48453,53033) group by \n" +
                    "w.geoidDestination").getResultList();
            SunburstResponse response=null;
            for(Object[] resultSet : queryResponses){
                response=new SunburstResponse();
                response.setName(resultSet[0].toString());
                response.setValue(Double.parseDouble(resultSet[2].toString()));
                if(map.get(resultSet[1].toString())==null){
                    SunburstResponse state=new SunburstResponse();
                    state.setName(resultSet[1].toString());
                    state.setValue(Double.parseDouble(resultSet[2].toString()));
                    List<SunburstResponse> children=new ArrayList<>();
                    children.add(response);
                    state.setChildren(children);
                    map.put(resultSet[1].toString(),state);
                }else{
                    SunburstResponse state=map.get(resultSet[1].toString());
                    state.setValue(state.getValue()+Double.parseDouble(resultSet[2].toString()));
                    state.getChildren().add(response);
                }
            }
            SunburstResponse parent=new SunburstResponse();
            parent.setName("The USA");
            parent.setValue(0);
            List<SunburstResponse> childrenList=new ArrayList<>();
            parent.setChildren(childrenList);
            for(String key : map.keySet()){
                parent.setValue(parent.getValue()+map.get(key).getValue());
                parent.getChildren().add(map.get(key));
            }
            ObjectMapper objectMapper=new ObjectMapper();
            //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            System.out.println(objectMapper.writeValueAsString(parent));


        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print(exception.getLocalizedMessage());

        }
    }
    public static void main(String ar[]){
        SunburstBuilder builder=new SunburstBuilder();
        builder.fetchSunburstResponse();
    }
}
