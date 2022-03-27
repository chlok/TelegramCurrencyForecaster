package ru.liga.repository;

import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.time.LocalDate;
import java.util.List;


public interface RatesRepository {

    /**
     * Метод возвращает курсы вылюты за весь период в зависимости от указанной валюты
     *
     * @param currency название валюты
     */
    List<Rate> getAllRates(Currency currency);


    /**
     * Метод возвращает курсы вылюты за последние 7 дней
     *
     * @param currency название валюты
     * @param days     количество дней, ставки за которые нам нужно получить
     */
    List<Rate> getPeriodRates(Currency currency, int days);

    /**
     * метод возвращает список курсов двух- и трехлетней давности на ту же дату
     *
     * @param currency валюта
     * @param date     дата
     * @return список курсов
     */
    List<Rate> getTwoAndThreeYearsThisDateRates(Currency currency, LocalDate date);

    /**
     * метод возвращает список ставок за даты, приходившиеся на последние полнолуния
     *
     * @param currency валюта
     * @param amount   количество полнолуний
     * @return список ставок
     */
    List<Rate> getLastFullMoonsRates(Currency currency, int amount);

    /**
     * метод для получения списка дат, которые придутся на полнолуния в будущем
     *
     * @return список дат
     */
    List<LocalDate> getFutureMoonDates();
}

