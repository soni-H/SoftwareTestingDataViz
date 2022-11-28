package com.example.hm.pojo;

import java.util.List;

public class SunburstResponse {

    private String name;
    private double value;

    @Override
    public String toString() {
        return "SunburstResponse{" +
                "name='" + name + '\'' +
                ", totalFlow=" + value +
                ", children=" + children +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<SunburstResponse> getChildren() {
        return children;
    }

    public void setChildren(List<SunburstResponse> children) {
        this.children = children;
    }

    private List<SunburstResponse> children;
}
