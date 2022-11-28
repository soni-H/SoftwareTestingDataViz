package com.example.hm.beans;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class CountyToState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer countyId;

    @Column(nullable = false)
    Integer geoidCounty;

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getGeoidCounty() {
        return geoidCounty;
    }

    public void setGeoidCounty(Integer geoidCounty) {
        this.geoidCounty = geoidCounty;
    }

    public Integer getGeoidState() {
        return geoidState;
    }

    public void setGeoidState(Integer geoidState) {
        this.geoidState = geoidState;
    }

    @Column(nullable = false)
    Integer geoidState;


}
