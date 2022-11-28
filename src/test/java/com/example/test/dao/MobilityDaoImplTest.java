package com.example.test.dao;

import com.example.dao.impl.MobilityDaoImpl;
import com.example.pojo.PopulationResponse;
import com.example.utils.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateSessionUtil.class)
@SuppressStaticInitializationFor("com.example.humanmobility.utils.HibernateSessionUtil")
public class MobilityDaoImplTest {

    @Mock
    Session session;

    @Mock
    List queryResponses1;

    @Mock
    List queryResponses2;

    @Mock
    List queryResponses3;

    @Mock
    Query mockQuery;


    MobilityDaoImpl mockMobilityDaoImpl;

    String fromDate="13-01-2021";
    String toDate="20-01-2021";

    @Before
    public void setup(){

        queryResponses1=new ArrayList<Object[]>();
        Object[] query1= new Object[]{"1000","13-01-2019","Ohio"};
        Object[] query2= new Object[]{"2000","17-01-2020","Canada"};

        queryResponses1.add(query1);
        queryResponses1.add(query2);

        queryResponses2=new ArrayList<Object[]>();
        queryResponses2.add(query1);

        queryResponses3=new ArrayList<Object[]>();


        PowerMockito.mockStatic(HibernateSessionUtil.class);
        mockMobilityDaoImpl=new MobilityDaoImpl();


    }

    @Test
    public void if_for_2() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";

        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses1);

        List<PopulationResponse> responses=mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);
        assertEquals(1000,responses.get(0).getPopulation());
        assertEquals("13-01-2019",responses.get(0).getDate());
        assertEquals("Ohio",responses.get(0).getLocationName());

        assertEquals(2000,responses.get(1).getPopulation());
        assertEquals("17-01-2020",responses.get(1).getDate());
        assertEquals("Canada",responses.get(1).getLocationName());


    }

    @Test
    public void if_for_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses2);

        List<PopulationResponse> responses=mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);
        assertEquals(1000,responses.get(0).getPopulation());
        assertEquals("13-01-2019",responses.get(0).getDate());
        assertEquals("Ohio",responses.get(0).getLocationName());

    }


    @Test
    public void if_for_0() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses3);

        List<PopulationResponse> responses=mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);
        assertEquals(0,responses.size());

    }


    @Test
    public void else_for_2() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses1);

        List<PopulationResponse> responses=mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);
        assertEquals(1000,responses.get(0).getPopulation());
        assertEquals("13-01-2019",responses.get(0).getDate());
        assertEquals("Ohio",responses.get(0).getLocationName());

        assertEquals(2000,responses.get(1).getPopulation());
        assertEquals("17-01-2020",responses.get(1).getDate());
        assertEquals("Canada",responses.get(1).getLocationName());

    }

    @Test
    public void else_for_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses2);

        List<PopulationResponse> responses=mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);
        assertEquals(1000,responses.get(0).getPopulation());
        assertEquals("13-01-2019",responses.get(0).getDate());
        assertEquals("Ohio",responses.get(0).getLocationName());

    }


    @Test
    public void else_for_0() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses3);

        List<PopulationResponse> responses=mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);
        //response=response.substring(1,response.length()-1);
        assertEquals(0,responses.size());

    }

    @Test(expected = Exception.class)
    public void throw_session_exception() {

        Mockito.doThrow(HibernateException.class).when(HibernateSessionUtil.getSession());
        String state="24";
        mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);


    }
    @Test(expected = Exception.class)
    public void throw_create_query_else() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.doThrow(HibernateException.class).when(session.createQuery(anyString()));
        mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);

    }

    @Test(expected = Exception.class)
    public void throw_create_query_if() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.doThrow(HibernateException.class).when(session.createQuery(anyString()));
        mockMobilityDaoImpl.viewPopulationTimeSeries(state,fromDate,toDate);

    }


}
