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
    List queryResponses;

    @Mock
    Query mockQuery;


    FlowMatrixImpl mockFlowMatrixImpl;

    @Before
    public void setup(){
        //MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(HibernateSessionUtil.class);
        mockFlowMatrixImpl=new FlowMatrixImpl();
        System.out.println("before test ran");


    }

    @Test
    public void endtoend() throws Exception {
        //doReturn(session).when(localMockRepository).getSession();
        queryResponses=new ArrayList<Object[]>();
        Object[] query1= new Object[]{"1000","California","Ohio"};
        Object[] query2= new Object[]{"2000","Hawaii","LA"};
        queryResponses.add(query1);
        queryResponses.add(query2);
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        String fromDate="13-01-2021";
        String toDate="20-01-2021";
        //System.out.println("Session is "+session);
        Mockito.when(session.createQuery(anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(anyString(),any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(queryResponses);

        //doReturn(queryResponses).when(session).createQuery(anyString()).getResultList();
        String response=mockFlowMatrixImpl.getFlowMatrix(state,fromDate,toDate);
        System.out.println(response);
        response=response.substring(1,response.length()-1);
        //System.out.println(response);
        List<String> responseList = Arrays.asList(response.split("},"));

        HeatMapCell cell1 = new ObjectMapper().readValue(responseList.get(0)+"}", HeatMapCell.class);
        HeatMapCell cell2 = new ObjectMapper().readValue(responseList.get(1)+"}", HeatMapCell.class);
        assertEquals(1000.0,cell1.getHeat(),0.0);
        assertEquals("Ohio",cell1.getX());
        assertEquals("California",cell1.getY());

    }


}
