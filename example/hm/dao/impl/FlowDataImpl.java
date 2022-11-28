package com.example.hm.dao.impl;

import com.example.hm.dao.FlowDataConnection;
import com.example.hm.pojo.FlowData;
import com.example.hm.pojo.Label;
import com.example.hm.utils.HibernateSessionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FlowDataImpl implements FlowDataConnection {
    @Override
    public String getFlowConnectors(String state,String fromDate,String toDate) {
        String response=null;
        try (Session session = HibernateSessionUtil.getSession()) {
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date from,to;
            from=formatter.parse(fromDate);
            to=formatter.parse(toDate);
            List<Object[]> queryResponses=null;
            if(state.equalsIgnoreCase("All")) {
                queryResponses = session.createQuery("select distinct g.latitude,g.longitude,g2.latitude,g2.longitude,\n" +
                        "sum(w.populationFlow),g.locationName as Origin,g2.locationName as Destination from WeeklyFlow  w \n" +
                        "inner join Gazetteer g on substring(w.geoidOrigin,1,2)=cast(g.geoid as string) inner join Gazetteer g2 on \n" +
                        "substring(w.geoidDestination,1,2)=cast(g2.geoid as string) where w.dateTo<=:to and\n" +
                        "w.dateFrom>=:from group by g.geoid,\n" +
                        "g2.geoid").setParameter("from", from).setParameter("to", to).getResultList();
            }else{
                int stateNumber=Integer.parseInt(state);
                queryResponses = session.createQuery("select w.latitudeOrigin,w.longitudeOrigin,w.latitudeDestination,w.longitudeDestination,\n" +
                        "sum(w.populationFlow),g.locationName as Origin,g2.locationName as Destination from WeeklyFlow w \n" +
                        "inner join Gazetteer g on w.geoidOrigin=g.geoid inner join Gazetteer g2 on \n" +
                        "w.geoidDestination=g2.geoid where substring(w.geoidOrigin,1,2) =:state and substring(w.geoidDestination,1,2) =:state  and w.dateTo<=:toDate and\n" +
                        "w.dateFrom>=:fromDate group by w.geoidOrigin, w.geoidDestination ")
                        .setParameter("fromDate", from).setParameter("toDate", to).setParameter("state",state).getResultList();
            }
            Label label=new Label();
            label.setAnchor("bottom");
            label.setPosition("0%");

            List<FlowData> connections=new ArrayList<>();
            FlowData connection=null;

            for(Object[] resultset : queryResponses) {
                connection = new FlowData();
                connection.setLabel(label);
                List<Double> points=new ArrayList<>();

                points.add(Double.parseDouble(resultset[0].toString()));
                points.add(Double.parseDouble(resultset[1].toString()));
                points.add(Double.parseDouble(resultset[2].toString()));
                points.add(Double.parseDouble(resultset[3].toString()));

                connection.setPoints(points);
                connection.setTotal(Integer.parseInt(resultset[4].toString()));
                connection.setFrom(resultset[5].toString());
                connection.setTo(resultset[6].toString());
                connection.setCurvature(0.5f);
                connections.add(connection);
            }

            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            response=objectMapper.writeValueAsString(connections);

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print(exception.getLocalizedMessage());

        }
        return response;
    }
}
