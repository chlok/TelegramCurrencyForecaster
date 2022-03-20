package ru.liga.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.liga.model.Rate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static ru.liga.utils.DateTimeUtil.PARSE_FORMATTER;

public class Graph {
    private static final String GRAPH_NAME = "Currency Forecast";
    private static final String DOMAIN = "date";
    private static final String VALUE = "value";


    public byte[] drawGraph(List<List<Rate>> ratesList, ForecastPeriod forecastPeriod) {

        CategoryDataset dataset = createDataset(ratesList);
        JFreeChart chart = ChartFactory.createLineChart(GRAPH_NAME + "\n" + forecastPeriod , DOMAIN, VALUE, dataset);
        return getImageByteArray(chart);
    }

    private CategoryDataset createDataset(List<List<Rate>> ratesList) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (List<Rate> rates : ratesList) {
            for (Rate rate : rates) {
                LocalDate date = rate.getDate();
                dataset.addValue(rate.getRate(), rate.getCurrency(), date.format(PARSE_FORMATTER));
            }
        }
        return dataset;
    }


    private byte[] getImageByteArray (JFreeChart chart) {
        BufferedImage objBufferedImage = chart.createBufferedImage(600, 800);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", bas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("resources");
        return bas.toByteArray();
    }
}

