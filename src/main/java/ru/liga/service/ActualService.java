package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.ForecastPeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActualService implements ForecastService {

    private final RatesRepository repository;


    public ActualService(RatesRepository repository) {
        this.repository = repository;
    }

    /**
     * метод для получения курса на определенную дату
     *
     * @param currency валюта
     * @param date     дата
     * @return курс
     */
    @Override
    public Rate getRate(Currency currency, LocalDate date) {
        List<Rate> lastYearsRates = repository.getTwoAndThreeYearsThisDateRates(currency, date);
        if (lastYearsRates.size() != 2) {
            throw new IllegalArgumentException("для указанной даты нет актуальных данных за предыдущие годы для расчета");
        }
        int newRate = 0;
        for (Rate rate : lastYearsRates) {
            newRate += rate.getRate();
        }
        return new Rate(date, newRate, currency);
    }

    /**
     * метод для получения списка курсов на определенный период
     *
     * @param currency валюта
     * @param period   период
     * @return список курсов
     */
    @Override
    public List<Rate> getPeriodRates(Currency currency, ForecastPeriod period) {
        List<Rate> rates = new ArrayList<>();
        LocalDate lastDate = null;
        switch (period) {
            case WEEK -> lastDate = LocalDate.now().plusDays(7);
            case MONTH -> lastDate = LocalDate.now().plusMonths(1);
        }
        LocalDate currentDate = LocalDate.now();
        while (!currentDate.isAfter(lastDate)) {
            currentDate = currentDate.plusDays(1);
            rates.add(getRate(currency, currentDate));
        }
        return rates;
    }
}
