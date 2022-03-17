package ru.liga.repository;

import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.time.LocalDate;
import java.util.List;


public interface RatesRepository {
    List<Rate> getAllRates(Currency currency);

    List<Rate> getPeriodRates(Currency currency, int days);

    List<Rate> getTwoAndThreeYearsThisDateRates(Currency currency, LocalDate date);

    void addRate(Rate rate, Currency currency);

    List<Rate> getLastFullMoonsRates(Currency currency, int amount);

    List<LocalDate> getFutureMoonDates();
}

