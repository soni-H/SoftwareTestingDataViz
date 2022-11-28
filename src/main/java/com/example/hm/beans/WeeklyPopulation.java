package com.example.hm.beans;

import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table
public class WeeklyPopulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer populationId;

    @Column(nullable = false)
    private int geoid;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private Date date;

    public Integer getPopulationId() {
        return populationId;
    }

    public void setPopulationId(Integer populationId) {
        this.populationId = populationId;
    }

    public int getGeoid() {
        return geoid;
    }

    public void setGeoid(int geoid) {
        this.geoid = geoid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    @Column
    private int visitors;

    @Column(nullable = false)
    private int population;


}
