package com.example.hm.dao;

import com.example.hm.dao.impl.HierariachalFlowImpl;
import com.example.hm.pojo.SunburstResponse;
import com.example.hm.utils.HibernateSessionUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class HierariachalFlowImplTest {

    @Mock
    Session session;

    @Mock
    List queryResponsesOrigin1;

    @Mock
    List queryResponsesOrigin2;

    @Mock
    List queryResponsesOrigin3;


    @Mock
    List queryResponsesDest1;

    @Mock
    List queryResponsesDest2;

    @Mock
    List queryResponsesDest3;

    @Mock
    Query mockQueryOrigin;

    @Mock
    Query mockQueryDest;


    HierariachalFlowImpl mockHierariachalFlowImpl;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    String fromDate="13-01-2021";
    String toDate="20-01-2021";
    @Before
    public void setup(){

        queryResponsesOrigin1=new ArrayList<Object[]>();
        Object[] query1= new Object[]{"LA","California","1000"};
        Object[] query2= new Object[]{"Ohio","California","2000"};

        queryResponsesOrigin1.add(query1);
        queryResponsesOrigin1.add(query2);

        queryResponsesOrigin2=new ArrayList<Object[]>();
        queryResponsesOrigin2.add(query1);

        queryResponsesOrigin3=new ArrayList<Object[]>();


        queryResponsesDest1=new ArrayList<Object[]>();
        Object[] query3= new Object[]{"Mumbai","India","3000"};
        Object[] query4= new Object[]{"Delhi","India","4000"};

        queryResponsesDest1.add(query3);
        queryResponsesDest1.add(query4);

        queryResponsesDest2=new ArrayList<Object[]>();
        queryResponsesDest2.add(query3);

        queryResponsesDest3=new ArrayList<Object[]>();


        PowerMockito.mockStatic(HibernateSessionUtil.class);
        mockHierariachalFlowImpl=new HierariachalFlowImpl();


    }

    @Test
    public void if_for_2() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin1);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest1);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);
        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals("The USA",sunburstResponses.get(0).getName());
        assertEquals("India",sunburstResponses.get(0).getChildren().get(0).getName());
        assertEquals(7000,sunburstResponses.get(0).getChildren().get(0).getValue(),0);

        assertEquals("Mumbai",sunburstResponses.get(0).getChildren().get(0).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(0).getChildren().get(0).getChildren().get(0).getValue(),0);
        assertEquals("Delhi",sunburstResponses.get(0).getChildren().get(0).getChildren().get(1).getName());
        assertEquals(4000,sunburstResponses.get(0).getChildren().get(0).getChildren().get(1).getValue(),0);


        assertEquals("The USA",sunburstResponses.get(1).getName());
        assertEquals("California",sunburstResponses.get(1).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(1).getChildren().get(0).getValue(),0);

        assertEquals("LA",sunburstResponses.get(1).getChildren().get(0).getChildren().get(0).getName());
        assertEquals(1000,sunburstResponses.get(1).getChildren().get(0).getChildren().get(0).getValue(),0);
        assertEquals("Ohio",sunburstResponses.get(1).getChildren().get(0).getChildren().get(1).getName());
        assertEquals(2000,sunburstResponses.get(1).getChildren().get(0).getChildren().get(1).getValue(),0);

    }

    @Test
    public void else_for_2() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin1);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest1);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);
        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals("California",sunburstResponses.get(0).getName());
        assertEquals("LA",sunburstResponses.get(0).getChildren().get(0).getName());
        assertEquals(1000,sunburstResponses.get(0).getChildren().get(0).getValue(),0);
        assertEquals("Ohio",sunburstResponses.get(0).getChildren().get(1).getName());
        assertEquals(2000,sunburstResponses.get(0).getChildren().get(1).getValue(),0);

        assertEquals("India",sunburstResponses.get(1).getName());
        assertEquals("Mumbai",sunburstResponses.get(1).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(1).getChildren().get(0).getValue(),0);
        assertEquals("Delhi",sunburstResponses.get(1).getChildren().get(1).getName());
        assertEquals(4000,sunburstResponses.get(1).getChildren().get(1).getValue(),0);


    }

    @Test
    public void else_for_1_2() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin2);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest1);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);
        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals("California",sunburstResponses.get(0).getName());
        assertEquals("LA",sunburstResponses.get(0).getChildren().get(0).getName());
        assertEquals(1000,sunburstResponses.get(0).getChildren().get(0).getValue(),0);


        assertEquals("India",sunburstResponses.get(1).getName());
        assertEquals("Mumbai",sunburstResponses.get(1).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(1).getChildren().get(0).getValue(),0);
        assertEquals("Delhi",sunburstResponses.get(1).getChildren().get(1).getName());
        assertEquals(4000,sunburstResponses.get(1).getChildren().get(1).getValue(),0);


    }

    @Test
    public void else_for_2_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin1);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest2);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);
        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals("California",sunburstResponses.get(0).getName());
        assertEquals("LA",sunburstResponses.get(0).getChildren().get(0).getName());
        assertEquals(1000,sunburstResponses.get(0).getChildren().get(0).getValue(),0);
        assertEquals("Ohio",sunburstResponses.get(0).getChildren().get(1).getName());
        assertEquals(2000,sunburstResponses.get(0).getChildren().get(1).getValue(),0);

        assertEquals("India",sunburstResponses.get(1).getName());
        assertEquals("Mumbai",sunburstResponses.get(1).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(1).getChildren().get(0).getValue(),0);



    }

    @Test
    public void if_for_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin1);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest1);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals("The USA",sunburstResponses.get(0).getName());
        assertEquals("India",sunburstResponses.get(0).getChildren().get(0).getName());
        assertEquals(7000,sunburstResponses.get(0).getChildren().get(0).getValue(),0);
        assertEquals("Mumbai",sunburstResponses.get(0).getChildren().get(0).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(0).getChildren().get(0).getChildren().get(0).getValue(),0);


        assertEquals("The USA",sunburstResponses.get(1).getName());
        assertEquals("California",sunburstResponses.get(1).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(1).getChildren().get(0).getValue(),0);
        assertEquals("LA",sunburstResponses.get(1).getChildren().get(0).getChildren().get(0).getName());
        assertEquals(1000,sunburstResponses.get(1).getChildren().get(0).getChildren().get(0).getValue(),0);
    }

    @Test
    public void else_for_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin1);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest1);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals("California",sunburstResponses.get(0).getName());
        assertEquals("LA",sunburstResponses.get(0).getChildren().get(0).getName());
        assertEquals(1000,sunburstResponses.get(0).getChildren().get(0).getValue(),0);


        assertEquals("India",sunburstResponses.get(1).getName());
        assertEquals("Mumbai",sunburstResponses.get(1).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(1).getChildren().get(0).getValue(),0);
    }

    @Test
    public void else_for_1_0() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin2);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest3);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals("California",sunburstResponses.get(0).getName());
        assertEquals("LA",sunburstResponses.get(0).getChildren().get(0).getName());
        assertEquals(1000,sunburstResponses.get(0).getChildren().get(0).getValue(),0);


        assertEquals(0,sunburstResponses.get(1).getChildren().size());
        assertEquals(null,sunburstResponses.get(1).getName());
    }

    @Test
    public void else_for_0_1() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin3);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest2);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});

        assertEquals(0,sunburstResponses.get(0).getChildren().size());
        assertEquals(null,sunburstResponses.get(0).getName());


        assertEquals("India",sunburstResponses.get(1).getName());
        assertEquals("Mumbai",sunburstResponses.get(1).getChildren().get(0).getName());
        assertEquals(3000,sunburstResponses.get(1).getChildren().get(0).getValue(),0);
    }


    @Test
    public void if_for_0() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin3);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest3);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});
        assertEquals(2,sunburstResponses.size());
        assertEquals(0,sunburstResponses.get(0).getChildren().size());
        assertEquals("The USA",sunburstResponses.get(0).getName());


        assertEquals(0,sunburstResponses.get(1).getChildren().size());
        assertEquals("The USA",sunburstResponses.get(1).getName());



    }


    @Test
    public void else_for_0() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";

        Mockito.when(session.createQuery(Mockito.matches("(.*Origin.*)"))).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.setParameter(anyString(),any())).thenReturn(mockQueryOrigin);
        Mockito.when(mockQueryOrigin.getResultList()).thenReturn(queryResponsesOrigin3);

        Mockito.when(session.createQuery(Mockito.matches("(.*Destination.*)"))).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.setParameter(anyString(),any())).thenReturn(mockQueryDest);
        Mockito.when(mockQueryDest.getResultList()).thenReturn(queryResponsesDest3);

        String responses=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

        List<SunburstResponse> sunburstResponses = new ObjectMapper().readValue(responses, new TypeReference<List<SunburstResponse>>(){});
        assertEquals(2,sunburstResponses.size());
        assertEquals(0,sunburstResponses.get(0).getChildren().size());
        assertEquals(null,sunburstResponses.get(0).getName());


        assertEquals(0,sunburstResponses.get(1).getChildren().size());
        assertEquals(null,sunburstResponses.get(1).getName());



    }

    @Test(expected = Exception.class)
    public void throw_session_exception() {

        Mockito.doThrow(HibernateException.class).when(HibernateSessionUtil.getSession());
        String state="24";String response=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);


    }
    @Test(expected = Exception.class)
    public void throw_create_query_else() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="24";
        Mockito.doThrow(HibernateException.class).when(session.createQuery(anyString()));
        String response=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

    }

    @Test(expected = Exception.class)
    public void throw_create_query_if() throws Exception {
        when(HibernateSessionUtil.getSession()).thenReturn(session);
        String state="All";
        Mockito.doThrow(HibernateException.class).when(session.createQuery(anyString()));
        String response=mockHierariachalFlowImpl.viewHierariachalFlow(state,fromDate,toDate);

    }




}
