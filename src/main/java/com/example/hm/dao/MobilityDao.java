package com.example.hm.dao;

import com.example.hm.pojo.PopulationResponse;

import java.util.List;

public interface MobilityDao {

public List<PopulationResponse> viewPopulationTimeSeries(String state,String fromDate,String toDate);
}
