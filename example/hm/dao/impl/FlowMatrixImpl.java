package com.example.hm.dao.impl;

import com.example.hm.dao.FlowMatrix;
import com.example.hm.pojo.HeatMapCell;
import com.example.hm.utils.HibernateSessionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FlowMatrixImpl implements FlowMatrix {
    @Override
    public String getFlowMatrix(String state, String fromDate, String toDate) {
        //N21:
        String parentResponse=null;
        //N22:
        try (Session session = HibernateSessionUtil.getSession()) {
            //N24:
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date from, to;
            from = formatter.parse(fromDate);
            to = formatter.parse(toDate);
            List<Object[]> queryResponses = null;
            if (state.equalsIgnoreCase("All")) {
                //N25:
                queryResponses = session.createQuery("select sum(w.populationFlow),g.locationName as Origin,g2.locationName as Destination from WeeklyFlow  w \n" +
                        "inner join Gazetteer g on substring(w.geoidOrigin,1,2)=cast(g.geoid as string) inner join Gazetteer g2 on \n" +
                        "substring(w.geoidDestination,1,2)=cast(g2.geoid as string) where w.dateTo<=:to and\n" +
                        "w.dateFrom>=:from group by g.geoid,\n" +
                        "g2.geoid order by g.geoid,g2.geoid").setParameter("from", from).setParameter("to", to).getResultList();
            }else{
                //N26:
                int stateNumber=Integer.parseInt(state);
                queryResponses = session.createQuery("select sum(w.populationFlow),g1.locationName,g2.locationName from Gazetteer  g1 inner join Gazetteer g2 on 1=1 \n" +
                                " and g1.geoid<> :sno and g2.geoid<>:sno and substring(g1.geoid,1,2)=:state and substring(g2.geoid,1,2) =:state\n" +
                                "left outer join WeeklyFlow w on g1.geoid=w.geoidOrigin and \n" +
                                "g2.geoid=w.geoidDestination and w.dateTo<=:toDate and w.dateFrom>=:fromDate where g1.geoid is not null and \n" +
                                "g2.geoid is not null group by \n" +
                                "g1.geoid,g2.geoid order by g1.geoid,g2.geoid")
                        .setParameter("fromDate", from).setParameter("toDate", to).setParameter("state",state).setParameter("sno",stateNumber).getResultList();
            }
            //N27:
            List<HeatMapCell> response=new ArrayList<>();
            HeatMapCell heatMapCell=null;
            for(Object[] resultSet : queryResponses){
                heatMapCell=new HeatMapCell();
                if(resultSet[0]!=null)
                    heatMapCell.setHeat(Double.parseDouble(resultSet[0].toString()));
                else heatMapCell.setHeat(0);
                heatMapCell.setY(resultSet[1].toString().trim());
                heatMapCell.setX(resultSet[2].toString().trim());
                response.add(heatMapCell);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            parentResponse = objectMapper.writeValueAsString(response);
        }
        //N23:
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        return parentResponse;
    }
}
