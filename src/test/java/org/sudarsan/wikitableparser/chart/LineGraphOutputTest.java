package org.sudarsan.wikitableparser.chart;

import org.junit.jupiter.api.Test;
import org.sudarsan.wikitableparser.domain.ChartData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class LineGraphOutputTest {
    @Test
    public void testDrawChartForNullInput() {
        LineGraphOutput lineGraphOutput = new LineGraphOutput();
        lineGraphOutput.drawChart(null);
    }
    @Test
    public void testDrawChartForEmptyInput() {
        LineGraphOutput lineGraphOutput = new LineGraphOutput();
        lineGraphOutput.drawChart(new ArrayList<>());
    }

    @Test
    public void testDrawChart() {
        LineGraphOutput lineGraphOutput = new LineGraphOutput();
        lineGraphOutput.drawChart(Arrays.asList(new ChartData("1990", 100), new ChartData("2000", 500), new ChartData("2010", 365), new ChartData("2020", 463)));
        File file = new File(LineGraphOutput.getChartOutputPath());
        assertTrue(file.exists());
    }

}
