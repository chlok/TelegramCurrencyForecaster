package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.model.ForecastPeriod;

import java.time.LocalDate;
import java.util.List;

public interface ForecastService {

    /**
     * метод для получения курса на определенную дату
     *
     * @param currency валюта
     * @param date     дата
     * @return курс
     */
    Rate getRate(Currency currency, LocalDate date);


    /**
     * метод для получения списка курсов на определенный период
     *
     * @param currency валюта
     * @param lastDate - дата, до которой нужно сделать прогноз
     * @return список курсов
     */
    List<Rate> getPeriodRates(Currency currency, LocalDate lastDate);
}
