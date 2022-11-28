package com.example.hm.beans;

import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Table
public class Gazetteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;

    @Column(nullable = false)
    private Integer geoid;

    @Column(nullable = false)
    private String locationName;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getGeoid() {
        return geoid;
    }

    public void setGeoid(Integer geoid) {
        this.geoid = geoid;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    @Column(nullable = false)
    private double  latitude;

    @Column(nullable = false)
    private double  longitude;
}
