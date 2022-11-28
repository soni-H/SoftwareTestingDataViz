package com.example.hm.dao.impl;

import com.example.hm.dao.MobilityDao;
import com.example.hm.pojo.PopulationResponse;
import com.example.hm.utils.HibernateSessionUtil;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MobilityDaoImpl implements MobilityDao {

    @Override
    public List<PopulationResponse> viewPopulationTimeSeries(String state, String fromDate, String toDate) {

        //N21:
        List<PopulationResponse> populationResponses=new ArrayList<>();
        //N22:
        try (Session session = HibernateSessionUtil.getSession()) {
            //N23:
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date from,to;
            from=formatter.parse(fromDate);
            to=formatter.parse(toDate);

            List<Object[]> queryResponses=null;
            if(state.equalsIgnoreCase("All")) {
                queryResponses = session.createQuery("select sum(a.population),a.date,b.locationName from WeeklyPopulation  a inner join Gazetteer b " +
                                "on substring(a.geoid,1,2) =cast(b.geoid as string) \n" +
                                "where a.date between :fromDate and :toDate group by b.locationName,a.date order by a.date")
                        .setParameter("fromDate", from).setParameter("toDate", to).getResultList();
            }else{
                int stateNumber=Integer.parseInt(state);
                queryResponses = session.createQuery("select a.population,a.date,c.locationName from WeeklyPopulation  a inner join Gazetteer b on " +
                                "b.geoid=:state and substring(a.geoid,1,2) =cast(b.geoid as string ) inner join Gazetteer c \n" +
                                "on a.geoid=c.geoid where a.date between :fromDate and :toDate order by a.date").setParameter("state", stateNumber)
                        .setParameter("fromDate", from).setParameter("toDate", to).getResultList();
            }

            for(Object[] row : queryResponses){
             PopulationResponse populationResponse=new PopulationResponse();
             populationResponse.setPopulation(Integer.parseInt(row[0].toString()));
             populationResponse.setLocationName(row[2].toString());
             populationResponse.setDate(row[1].toString());
             populationResponses.add(populationResponse);
            }

        } //N24:
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.print(exception.getLocalizedMessage());

        }
        return populationResponses;
    }
}
