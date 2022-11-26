package com.example.humanmobility.test.dao;


import com.example.humanmobility.dao.impl.FlowMatrixImpl;
import com.example.humanmobility.pojo.HeatMapCell;
import com.example.humanmobility.utils.HibernateSessionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.events.ExceptionThrownEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateSessionUtil.class)
@SuppressStaticInitializationFor("com.example.humanmobility.utils.HibernateSessionUtil")
public class FlowDataImplTest {


    //HibernateSessionUtil localMockRepository;

    @Mock
    Session session;

    @Mock
    List queryResponses1;

    @Mock
    List queryResponses2;

    @Mock
    List queryResponses3;

    @Mock
    List queryResponses4;

    @Mock
    List queryResponses5;

    @Mock
    Query mockQuery;


    FlowMatrixImpl mockFlowMatrixImpl;

    String fromDate="13-01-2021";
    String toDate="20-01-2021";

    @Before
    public void setup(){

        queryResponses1=new ArrayList<Object[]>();
        Object[] query1= new Object[]{"1000","California","Ohio"};
        Object[] query2= new Object[]{"2000","Hawaii","LA"};

        Object[] query3= new Object[]{null,"California","Ohio"};
        Object[] query4= new Object[]{null,"Hawaii","LA"};
        queryResponses1.add(query1);
        queryResponses1.add(query2);

        queryResponses2=new ArrayList<Object[]>();
        queryResponses2.add(query1);

        queryResponses3=new ArrayList<Object[]>();

        queryResponses4=new ArrayList<Object[]>();
        queryResponses4.add(query1);
        queryResponses4.add(query4);

        queryResponses5=new ArrayList<Object[]>();
        queryResponses4.add(query4);

        PowerMockito.mockStatic(HibernateSessionUtil.class);
        mockFlowMatrixImpl=new FlowMatrixImpl();


    }

    @Test
    public void if_for_2_if() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses1);

        String response=mockFlowMatrixImpl.getFlowMatrix(state,fromDate,toDate);
        response=response.substring(1,response.length()-1);
        List<String> responseList = Arrays.asList(response.split("},"));

        HeatMapCell cell1 = new ObjectMapper().readValue(responseList.get(0)+"}", HeatMapCell.class);
        HeatMapCell cell2 = new ObjectMapper().readValue(responseList.get(1)+"}", HeatMapCell.class);
        assertEquals(1000.0,cell1.getHeat(),0.0);
        assertEquals("Ohio",cell1.getX());
        assertEquals("California",cell1.getY());

        assertEquals(2000.0,cell2.getHeat(),0.0);
        assertEquals("LA",cell2.getX());
        assertEquals("Hawaii",cell2.getY());

    }

    @Test
    public void if_for_1_if() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";

        //System.out.println("Session is "+session);
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses2);

        String response=mockFlowMatrixImpl.getFlowMatrix(state,fromDate,toDate);
        response=response.substring(1,response.length()-1);
        List<String> responseList = Arrays.asList(response.split("},"));

        HeatMapCell cell1 = new ObjectMapper().readValue(responseList.get(0)+"}", HeatMapCell.class);
        assertEquals(1000.0,cell1.getHeat(),0.0);
        assertEquals("Ohio",cell1.getX());
        assertEquals("California",cell1.getY());


    }

    @Test
    public void if_for_0_if() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";

        //System.out.println("Session is "+session);
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses3);

        String response=mockFlowMatrixImpl.getFlowMatrix(state,fromDate,toDate);
        response=response.substring(1,response.length()-1);
        assertEquals("",response);


    }

    @Test
    public void if_for_2_else() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses1);

        String response=mockFlowMatrixImpl.getFlowMatrix(state,fromDate,toDate);
        response=response.substring(1,response.length()-1);
        List<String> responseList = Arrays.asList(response.split("},"));

        HeatMapCell cell1 = new ObjectMapper().readValue(responseList.get(0)+"}", HeatMapCell.class);
        HeatMapCell cell2 = new ObjectMapper().readValue(responseList.get(1)+"}", HeatMapCell.class);
        assertEquals(1000.0,cell1.getHeat(),0.0);
        assertEquals("Ohio",cell1.getX());
        assertEquals("California",cell1.getY());

        assertEquals(2000.0,cell2.getHeat(),0.0);
        assertEquals("LA",cell2.getX());
        assertEquals("Hawaii",cell2.getY());

    }

    @Test
    public void if_for_1_else() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        //System.out.println("Session is "+session);
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses5);

        String response=mockFlowMatrixImpl.getFlowMatrix(state,fromDate,toDate);
        response=response.substring(1,response.length()-1);
        List<String> responseList = Arrays.asList(response.split("},"));

        HeatMapCell cell1 = new ObjectMapper().readValue(responseList.get(0)+"}", HeatMapCell.class);
        assertEquals(0,cell1.getHeat(),0.0);
        assertEquals("Ohio",cell1.getX());
        assertEquals("California",cell1.getY());


    }




}
