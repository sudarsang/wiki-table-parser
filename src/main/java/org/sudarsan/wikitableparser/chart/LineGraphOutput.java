package org.sudarsan.wikitableparser.chart;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.sudarsan.wikitableparser.domain.ChartData;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LineGraphOutput extends Application implements GraphOutput {
    private static List<ChartData> chartData;
    private static final String CHART_OUTPUT_PATH = ".\\output\\graph.png";
    private static final int CHART_WIDTH_PIXEL = 800;
    private static final int CHART_HEIGHT_PIXEL = 600;

    @Override
    public void start(Stage stage) throws Exception {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<String,Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        lineChart.setTitle("Wiki Table Extract");
        series.setName("Wiki Table Data");

        chartData.forEach(chartData -> series.getData().add(new XYChart.Data<>(chartData.getxData(), chartData.getyData())));

        lineChart.setAnimated(false);
        lineChart.getData().add(series);
        Scene scene  = new Scene(lineChart,CHART_WIDTH_PIXEL,CHART_HEIGHT_PIXEL);
        stage.setScene(scene);
//        stage.show();     //useful to test the output without saving

        try {
            WritableImage image = lineChart.snapshot(new SnapshotParameters(), new WritableImage(CHART_WIDTH_PIXEL, CHART_HEIGHT_PIXEL));
            File file = new File(CHART_OUTPUT_PATH);
            file.mkdirs();
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
            System.out.printf("Chart image saved successfully to %s", CHART_OUTPUT_PATH);
            Platform.exit();
        } catch (IOException e) {
            System.out.printf("Error in writing chart image: %s", e);
        }
    }

    @Override
    public void drawChart(List<ChartData> chartData) {
        if(chartData == null || chartData.size() == 0) {
            System.out.println("chartData cannot be null or empty");
            return;
        }

        System.out.println("Start drawing chart data");
        LineGraphOutput.chartData = chartData;
        launch();
    }

    public static String getChartOutputPath() {
        return CHART_OUTPUT_PATH;
    }
}
