package com.example.hm.dao.impl;

import com.example.hm.dao.HierariachalFlow;
import com.example.hm.pojo.SunburstResponse;
import com.example.hm.utils.HibernateSessionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierariachalFlowImpl implements HierariachalFlow {
    @Override
    public String viewHierariachalFlow(String state,String fromDate,String toDate) {
        Map<String, SunburstResponse> map=new HashMap();
        String parentResponse=null;
        try (Session session = HibernateSessionUtil.getSession()) {
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date from,to;
            from=formatter.parse(fromDate);
            to=formatter.parse(toDate);
            List<Object[]> queryResponsesDestination=null;
            List<Object[]> queryResponsesOrigin=null;

            if(state.equalsIgnoreCase("All")) {
                queryResponsesDestination = session.createQuery("select g.locationName,g2.locationName as State,sum(w.populationFlow) from Gazetteer g \n" +
                        "inner join WeeklyFlow w on g.geoid=w.geoidDestination inner join Gazetteer g2 on \n" +
                        "substring(w.geoidDestination,1,2) =cast(g2.geoid as string )\n" +
                        "where w.geoidDestination in (4013,6001,6037,6059,6065,6067,6071,6073,6085,12011,12057,12086,\n" +
                        "12095,12099,15003,17031,26163,32003,\n" +
                        "36047,36059,36081,36103,39049,42101,48029,48113,48201,48439,48453,53033) and w.dateFrom >= :from and w.dateTo<=:to group by \n" +
                        "w.geoidDestination").setParameter("from", from).setParameter("to", to).getResultList();

                queryResponsesOrigin = session.createQuery("select g.locationName,g2.locationName as State,sum(w.populationFlow) from Gazetteer g \n" +
                                "inner join WeeklyFlow w on g.geoid=w.geoidOrigin inner join Gazetteer g2 on \n" +
                                "substring(w.geoidOrigin,1,2) =cast(g2.geoid as string ) \n" +
                                "where w.geoidOrigin in (4013,6001,6037,6059,6065,6067,6071,6073,6085,12011,12057,12086,\n" +
                                "12095,12099,15003,17031,26163,32003,\n" +
                                "36047,36059,36081,36103,39049,42101,48029,48113,48201,48439,48453,53033) and w.dateFrom >= :from and w.dateTo<=:to group by w.geoidOrigin").
                        setParameter("from", from).setParameter("to", to).getResultList();

                List<SunburstResponse> parent = new ArrayList<>();
                parent.add(countFlows(queryResponsesDestination, new HashMap<>()));
                parent.add(countFlows(queryResponsesOrigin, new HashMap<>()));

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                parentResponse = objectMapper.writeValueAsString(parent);
            }else{
                int stateNumber=Integer.parseInt(state);
                queryResponsesOrigin=session.createQuery("select g.locationName,g2.locationName as State,sum(w.populationFlow) from Gazetteer g \n" +
                        "inner join WeeklyFlow w on g.geoid=w.geoidOrigin inner join Gazetteer g2 on \n" +
                        "substring(w.geoidOrigin,1,2) =cast(g2.geoid as string )\n" +
                        "where  g2.geoid=:state and w.dateFrom >= :from and w.dateTo<=:to group by w.geoidOrigin").setParameter("state",stateNumber).
                        setParameter("from",from).setParameter("to",to).getResultList();

                queryResponsesDestination=session.createQuery("select g.locationName,g2.locationName as State,sum(w.populationFlow) from Gazetteer g \n" +
                                "inner join WeeklyFlow w on g.geoid=w.geoidDestination inner join Gazetteer g2 on \n" +
                                "substring(w.geoidDestination,1,2) =cast(g2.geoid as string )\n" +
                                "where  g2.geoid=:state and w.dateFrom >= :from and w.dateTo<=:to group by w.geoidDestination").setParameter("state",stateNumber).
                        setParameter("from",from).setParameter("to",to).getResultList();

                SunburstResponse originParent=new SunburstResponse();

                List<SunburstResponse> children=new ArrayList();
                originParent.setChildren(children);
                SunburstResponse child=null;
                for(Object[] resultset: queryResponsesOrigin){
                    child=new SunburstResponse();
                    child.setValue(Double.parseDouble(resultset[2].toString()));
                    child.setName(resultset[0].toString());
                    children.add(child);
                    originParent.setName(resultset[1].toString());
                }

                SunburstResponse destinationParent=new SunburstResponse();

                List<SunburstResponse> childrenDest=new ArrayList();
                destinationParent.setChildren(childrenDest);
                SunburstResponse childDest=null;
                for(Object[] resultset: queryResponsesDestination){
                    childDest=new SunburstResponse();
                    childDest.setValue(Double.parseDouble(resultset[2].toString()));
                    childDest.setName(resultset[0].toString());
                    childrenDest.add(childDest);
                    destinationParent.setName(resultset[1].toString());
                }

                List<SunburstResponse> parent = new ArrayList<>();
                parent.add(originParent);
                parent.add(destinationParent);

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                parentResponse = objectMapper.writeValueAsString(parent);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print("Exception occurred is :"+exception.getLocalizedMessage());

        }
        return parentResponse;
    }
    public SunburstResponse countFlows(List<Object[]> queryResponses,Map<String,SunburstResponse> map){
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
        return parent;
    }
}
