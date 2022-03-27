package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.model.ForecastPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LinearRegressionService implements ForecastService {

    private final RatesRepository repository;

    public LinearRegressionService(RatesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Rate getRate(Currency currency, LocalDate date) {
        int daysUntilDate = (int) ChronoUnit.DAYS.between(LocalDate.now(), date);
        int lastMonthDays = (int) ChronoUnit.DAYS.between(LocalDate.now().minusMonths(1), LocalDate.now());
        LinearRegression linearRegression = getLinearRegression(lastMonthDays, date, currency);
        return new Rate(date, BigDecimal.valueOf(linearRegression.predict(lastMonthDays + daysUntilDate)), currency);

    }

    @Override
    public List<Rate> getPeriodRates(Currency currency, LocalDate lastDate) {
        List<Rate> rates = new ArrayList<>();
        int lastMonthDays = (int) ChronoUnit.DAYS.between(LocalDate.now().minusMonths(1), LocalDate.now());
        LinearRegression linearRegression = getLinearRegression(lastMonthDays, lastDate, currency);
        LocalDate currentDate = LocalDate.now();
        while (!currentDate.isAfter(lastDate)) {
            currentDate = currentDate.plusDays(1);
            int daysUntilDate = (int) ChronoUnit.DAYS.between(LocalDate.now(), currentDate);
            rates.add(new Rate(currentDate, BigDecimal.valueOf(linearRegression.predict(lastMonthDays + daysUntilDate)), currency));
        }
        return rates;
    }

    private LinearRegression getLinearRegression(int lastDaysForCount, LocalDate date, Currency currency) {
        Double[] rateValues = (Double[]) repository.getPeriodRates(currency, lastDaysForCount).stream()
                .map(rate -> rate.getRate().doubleValue())
                .toArray();

        double[] days = new double[rateValues.length];
        for (int i = 0; i < days.length; i++) {
            days[i] = i;
        }

        return new LinearRegression(days, rateValues);
    }
}

