package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.utils.ForecastPeriod;

import java.time.LocalDate;
import java.util.List;

public interface ForecastService {

    Rate getRate(Currency currency, LocalDate date);

    List<Rate> getPeriodRates(Currency currency, ForecastPeriod period);
}
