package ru.liga.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.App;
import ru.liga.model.Rate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ru.liga.utils.DateTimeUtil.PARSE_FORMATTER;

public class Graph {
    private static final Logger logger = LoggerFactory.getLogger(Graph.class);
    private static final String GRAPH_NAME = "Currency Forecast";
    private static final String DOMAIN = "date";
    private static final String VALUE = "value";
    public final String FILE_NAME = "C:\\Users\\chlok\\Desktop\\TelegramCurrencyForecaster\\src\\main\\resources\\image.png";

    /**
     * метод для рисования графика курсов валют
     *
     * @param ratesList список курсов
     */

    public void drawGraph(List<List<Rate>> ratesList) {

        CategoryDataset dataset = createDataset(ratesList);
        JFreeChart chart = ChartFactory.createLineChart(GRAPH_NAME, DOMAIN, VALUE, dataset);
        BufferedImage image = chart.createBufferedImage(800, 600);
        try {
            ImageIO.write(image, "png", new File(FILE_NAME));
        } catch (IOException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
        }
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

}
