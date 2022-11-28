package com.example.hm.utils;

import com.example.hm.beans.Gazetteer;
import com.example.hm.beans.WeeklyFlow;
import com.example.hm.beans.WeeklyPopulation;
import com.opencsv.CSVReader;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoadCSV {

    public boolean loadWeeklyFlowData(){
        try {

            List<WeeklyFlow> flowList = new ArrayList();
            List<WeeklyPopulation> populationList=new ArrayList<>();

            FileReader filereader = new FileReader("D:\\Semester-II\\Advanced Data Visualisations\\Data\\COVID19USFlows-WeeklyFlows\\weekly_flows\\county2county\\weekly_county2county_2020_02_10.csv");
            CSVReader csvReader = new CSVReader(filereader);

            boolean firstLine=true;
            WeeklyFlow flow;
            WeeklyPopulation population;

            String[] nextRecord;
            SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yy");
            Date sqldate;
            java.util.Date fromDate,toDate;
            String geoidOrigin,geoidDestination,latitudeOrigin,latitudeDestination,longitudeOrigin,longitudeDestination,dateRange,
                    visitorFlows,populationFlows;

            while ((nextRecord = csvReader.readNext()) != null) {
                if(firstLine) {
                    firstLine=false;
                    continue;
                }
                if(!nextRecord[0].equals(nextRecord[1])){
                    flow = new WeeklyFlow();
                    flow.setGeoidOrigin(Integer.parseInt(nextRecord[0]));
                    flow.setGeoidDestination(Integer.parseInt(nextRecord[1]));
                    flow.setVisitorFlow(Integer.parseInt(nextRecord[7]));
                    //System.out.println(nextRecord[7]+":"+nextRecord[8]);
                    flow.setPopulationFlow(Integer.parseInt(nextRecord[8]));

                    fromDate=formatter.parse(nextRecord[6].substring(0,8));
                    toDate=formatter.parse(nextRecord[6].substring(11));

                    flow.setDateFrom(new Date(fromDate.getTime()));
                    flow.setDateTo(new Date(toDate.getTime()));

                    flow.setLatitudeOrigin(Double.parseDouble(nextRecord[3]));
                    flow.setLongitudeOrigin(Double.parseDouble(nextRecord[2]));

                    flow.setLatitudeDestination(Double.parseDouble(nextRecord[5]));
                    flow.setLongitudeDestination(Double.parseDouble(nextRecord[4]));

                    flowList.add(flow);
                }else{
                    population=new WeeklyPopulation();
                    population.setGeoid(Integer.parseInt(nextRecord[0]));
                    population.setVisitors(Integer.parseInt(nextRecord[7]));
                    population.setPopulation(Integer.parseInt(nextRecord[8]));

                    toDate=formatter.parse(nextRecord[6].substring(11));
                    population.setDate(new Date(toDate.getTime()));

                    population.setLatitude(Double.parseDouble(nextRecord[3]));
                    population.setLongitude(Double.parseDouble(nextRecord[2]));

                    populationList.add(population);
                }
            }
            csvReader.close();
            filereader.close();

        //try (Session session = HibernateSessionUtil.getSession()) {
            Session session = HibernateSessionUtil.getSession();
            Transaction t = session.beginTransaction();
/*            for(WeeklyFlow f : flowList)
                session.save(f);
            t.commit();
*/
           for(WeeklyPopulation p : populationList)
                session.save(p);
            t.commit();

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print(exception.getLocalizedMessage());
            return false;
        }
    }
    public void loadGazetteer(){
        try {

            List<Gazetteer> gazetteerList = new ArrayList();


            FileReader filereader = new FileReader("D:\\Semester-II\\Advanced Data Visualisations\\Data\\2021_Gaz_counties_national\\Gz.csv");
            CSVReader csvReader = new CSVReader(filereader);

            boolean firstLine=true;
            Gazetteer gazetteer;

            String[] nextRecord;


            while ((nextRecord = csvReader.readNext()) != null) {
                if(firstLine) {
                    firstLine = false;
                    continue;
                }
                    gazetteer = new Gazetteer();
                gazetteer.setGeoid(Integer.parseInt(nextRecord[0]));
                gazetteer.setLocationName(nextRecord[1]);
                gazetteer.setLatitude(Double.parseDouble(nextRecord[2]));
                gazetteer.setLongitude(Double.parseDouble(nextRecord[3]));

                    gazetteerList.add(gazetteer);

            }
            csvReader.close();
            filereader.close();
            //try (Session session = HibernateSessionUtil.getSession()) {
            Session session = HibernateSessionUtil.getSession();
            Transaction t = session.beginTransaction();
            for(Gazetteer g : gazetteerList)
                session.save(g);
            t.commit();

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.print(exception.getLocalizedMessage());

        }
    }
    public static void main(String ar[]){
        LoadCSV loadCSV=new LoadCSV();
        loadCSV.loadWeeklyFlowData();
    }
}
