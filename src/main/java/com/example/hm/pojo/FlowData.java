package com.example.hm.pojo;

import java.util.List;

public class FlowData {

    private List<Double> points;
    private int total;
    private String from;
    private String to;

    public List<Double> getPoints() {
        return points;
    }

    public void setPoints(List<Double> points) {
        this.points = points;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public float getCurvature() {
        return curvature;
    }

    public void setCurvature(float curvature) {
        this.curvature = curvature;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    private float curvature;
    private Label label;
}
