package com.example.test.dao;

import com.example.dao.impl.FlowDataImpl;
import com.example.pojo.FlowData;
import com.example.utils.HibernateSessionUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class FlowDataImplTest {

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


    FlowDataImpl mockflowData;

    String fromDate="13-01-2021";
    String toDate="20-01-2021";

    @Before
    public void setup(){

        queryResponses1=new ArrayList<Object[]>();
        Object[] query1= new Object[]{"101.1","102.2","103.3","104.4","1000","California","Ohio"};
        Object[] query2= new Object[]{"201.1","202.2","203.3","204.4","2000","Hawaii","LA"};

        queryResponses1.add(query1);
        queryResponses1.add(query2);

        queryResponses2=new ArrayList<Object[]>();
        queryResponses2.add(query1);

        queryResponses3=new ArrayList<Object[]>();

        PowerMockito.mockStatic(HibernateSessionUtil.class);
        mockflowData=new FlowDataImpl();


    }

    @Test
    public void if_for_2() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses1);

        String response=mockflowData.getFlowConnectors(state,fromDate,toDate);
        List<FlowData> flows = new ObjectMapper().readValue(response, new TypeReference<List<FlowData>>(){});

        FlowData flow1=flows.get(0);
        FlowData flow2=flows.get(1);

        assertEquals(101.1,flow1.getPoints().get(0),0.0);
        assertEquals(102.2,flow1.getPoints().get(1),0.0);
        assertEquals(103.3,flow1.getPoints().get(2),0.0);
        assertEquals(104.4,flow1.getPoints().get(3),0.0);
        assertEquals("California",flow1.getFrom());
        assertEquals("Ohio",flow1.getTo());
        assertEquals(1000,flow1.getTotal());

        assertEquals(201.1,flow2.getPoints().get(0),0.0);
        assertEquals(202.2,flow2.getPoints().get(1),0.0);
        assertEquals(203.3,flow2.getPoints().get(2),0.0);
        assertEquals(204.4,flow2.getPoints().get(3),0.0);
        assertEquals("Hawaii",flow2.getFrom());
        assertEquals("LA",flow2.getTo());
        assertEquals(2000,flow2.getTotal());

    }

    @Test
    public void if_for_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses2);

        String response=mockflowData.getFlowConnectors(state,fromDate,toDate);
        List<FlowData> flows = new ObjectMapper().readValue(response, new TypeReference<List<FlowData>>(){});

        FlowData flow1=flows.get(0);

        assertEquals(101.1,flow1.getPoints().get(0),0.0);
        assertEquals(102.2,flow1.getPoints().get(1),0.0);
        assertEquals(103.3,flow1.getPoints().get(2),0.0);
        assertEquals(104.4,flow1.getPoints().get(3),0.0);
        assertEquals("California",flow1.getFrom());
        assertEquals("Ohio",flow1.getTo());
        assertEquals(1000,flow1.getTotal());

    }

    @Test
    public void if_for_0() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses3);

        String response=mockflowData.getFlowConnectors(state,fromDate,toDate);
        List<FlowData> flows = new ObjectMapper().readValue(response, new TypeReference<List<FlowData>>(){});
        assertEquals(0,flows.size());


    }

    @Test
    public void else_for_2() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses1);

        String response=mockflowData.getFlowConnectors(state,fromDate,toDate);
        List<FlowData> flows = new ObjectMapper().readValue(response, new TypeReference<List<FlowData>>(){});

        FlowData flow1=flows.get(0);
        FlowData flow2=flows.get(1);

        assertEquals(101.1,flow1.getPoints().get(0),0.0);
        assertEquals(102.2,flow1.getPoints().get(1),0.0);
        assertEquals(103.3,flow1.getPoints().get(2),0.0);
        assertEquals(104.4,flow1.getPoints().get(3),0.0);
        assertEquals("California",flow1.getFrom());
        assertEquals("Ohio",flow1.getTo());
        assertEquals(1000,flow1.getTotal());

        assertEquals(201.1,flow2.getPoints().get(0),0.0);
        assertEquals(202.2,flow2.getPoints().get(1),0.0);
        assertEquals(203.3,flow2.getPoints().get(2),0.0);
        assertEquals(204.4,flow2.getPoints().get(3),0.0);
        assertEquals("Hawaii",flow2.getFrom());
        assertEquals("LA",flow2.getTo());
        assertEquals(2000,flow2.getTotal());

    }

    @Test
    public void else_for_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses2);

        String response=mockflowData.getFlowConnectors(state,fromDate,toDate);
        List<FlowData> flows = new ObjectMapper().readValue(response, new TypeReference<List<FlowData>>(){});

        FlowData flow1=flows.get(0);

        assertEquals(101.1,flow1.getPoints().get(0),0.0);
        assertEquals(102.2,flow1.getPoints().get(1),0.0);
        assertEquals(103.3,flow1.getPoints().get(2),0.0);
        assertEquals(104.4,flow1.getPoints().get(3),0.0);
        assertEquals("California",flow1.getFrom());
        assertEquals("Ohio",flow1.getTo());
        assertEquals(1000,flow1.getTotal());

    }

    @Test
    public void else_for_0() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses3);

        String response=mockflowData.getFlowConnectors(state,fromDate,toDate);
        List<FlowData> flows = new ObjectMapper().readValue(response, new TypeReference<List<FlowData>>(){});
        assertEquals(0,flows.size());


    }

    @Test(expected = Exception.class)
    public void throw_session_exception() {

        Mockito.doThrow(HibernateException.class).when(HibernateSessionUtil.getSession());
        String state="24";
        mockflowData.getFlowConnectors(state,fromDate,toDate);


    }
    @Test(expected = Exception.class)
    public void throw_create_query_else() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.doThrow(HibernateException.class).when(session.createQuery(anyString()));
        mockflowData.getFlowConnectors(state,fromDate,toDate);

    }

    @Test(expected = Exception.class)
    public void throw_create_query_if() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.doThrow(HibernateException.class).when(session.createQuery(anyString()));
        mockflowData.getFlowConnectors(state,fromDate,toDate);

    }
}
