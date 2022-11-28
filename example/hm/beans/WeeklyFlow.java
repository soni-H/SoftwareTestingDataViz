package com.example.hm.beans;

import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table
public class WeeklyFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flowId;

    @Column(nullable = false)
    private int geoidOrigin;

    @Column(nullable = false)
    private int geoidDestination;

    @Column(nullable = false)
    private double latitudeOrigin;

    @Column(nullable = false)
    private double longitudeOrigin;

    @Column(nullable = false)
    private double latitudeDestination;

    @Column(nullable = false)
    private double longitudeDestination;

    @Column(nullable = false)
    private Date dateTo;

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public int getGeoidOrigin() {
        return geoidOrigin;
    }

    public void setGeoidOrigin(int geoidOrigin) {
        this.geoidOrigin = geoidOrigin;
    }

    public int getGeoidDestination() {
        return geoidDestination;
    }

    public void setGeoidDestination(int geoidDestination) {
        this.geoidDestination = geoidDestination;
    }

    public double getLatitudeOrigin() {
        return latitudeOrigin;
    }

    public void setLatitudeOrigin(double latitudeOrigin) {
        this.latitudeOrigin = latitudeOrigin;
    }

    public double getLongitudeOrigin() {
        return longitudeOrigin;
    }

    public void setLongitudeOrigin(double longitudeOrigin) {
        this.longitudeOrigin = longitudeOrigin;
    }

    public double getLatitudeDestination() {
        return latitudeDestination;
    }

    public void setLatitudeDestination(double latitudeDestination) {
        this.latitudeDestination = latitudeDestination;
    }

    public double getLongitudeDestination() {
        return longitudeDestination;
    }

    public void setLongitudeDestination(double longitudeDestination) {
        this.longitudeDestination = longitudeDestination;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public int getVisitorFlow() {
        return visitorFlow;
    }

    public void setVisitorFlow(int visitorFlow) {
        this.visitorFlow = visitorFlow;
    }

    public int getPopulationFlow() {
        return populationFlow;
    }

    public void setPopulationFlow(int populationFlow) {
        this.populationFlow = populationFlow;
    }

    @Column(nullable = false)
    private Date dateFrom;

    @Column
    private  int visitorFlow;

    @Column(nullable = false)
    private  int populationFlow;
}
