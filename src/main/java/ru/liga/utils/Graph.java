package ru.liga.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.liga.model.Rate;

import java.time.LocalDate;
import java.util.List;

import static ru.liga.utils.DateTimeUtil.PARSE_FORMATTER;

public class Graph {
    private static final String GRAPH_NAME = "Currency Forecast";
    private static final String DOMAIN = "date";
    private static final String VALUE = "value";


    /**
     * метод для рисования графика курсов валют
     *
     * @param ratesList      список курсов
     * @param forecastPeriod период прогнозирования
     */
    public void drawGraph(List<List<Rate>> ratesList, ForecastPeriod forecastPeriod) {

        CategoryDataset dataset = createDataset(ratesList);
        JFreeChart chart = ChartFactory.createLineChart(GRAPH_NAME + "\n" + forecastPeriod, DOMAIN, VALUE, dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        ApplicationFrame frame = new ApplicationFrame(GRAPH_NAME);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
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

