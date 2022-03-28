package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActualService implements ForecastService {

    private final RatesRepository repository;


    public ActualService(RatesRepository repository) {
        this.repository = repository;
    }


    @Override
    public Rate getRate(Currency currency, LocalDate date) {
        if (date.isAfter(LocalDate.now().plusYears(2))) {
            throw new IllegalArgumentException("для указанной даты нет актуальных данных за предыдущие годы для расчета");
        }
        List<Rate> lastYearsRates = repository.getTwoAndThreeYearsThisDateRates(currency, date);
        BigDecimal newRate = new BigDecimal(0);
        for (Rate rate : lastYearsRates) {
            newRate = newRate.add(rate.getRate());
        }
        return new Rate(date, newRate, currency);
    }


    @Override
    public List<Rate> getPeriodRates(Currency currency, LocalDate lastDate) {
        List<Rate> rates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        while (!currentDate.isAfter(lastDate)) {
            currentDate = currentDate.plusDays(1);
            rates.add(getRate(currency, currentDate));
        }
        return rates;
    }
}
