package org.sudarsan.wikitableparser.domain;

public class ChartData {
    private String xData;
    private double yData;

    public ChartData(String xData, double yData) {
        this.xData = xData;
        this.yData = yData;
    }

    public String getxData() {
        return xData;
    }

    public void setxData(final String xData) {
        this.xData = xData;
    }

    public double getyData() {
        return yData;
    }

    public void setyData(final double yData) {
        this.yData = yData;
    }
}
