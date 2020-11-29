package org.sudarsan.wikitableparser.chart;

import org.sudarsan.wikitableparser.domain.ChartData;

import java.util.List;

public interface GraphOutput {
    void drawChart(List<ChartData> chartData);
}
