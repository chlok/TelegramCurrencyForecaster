package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.utils.ForecastPeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MysticService implements ForecastService {
    private final RatesRepository repository;

    public MysticService(RatesRepository repository) {
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
        List<Rate> lastFullMoonsRates = repository.getLastFullMoonsRates(currency, 3);
        List<LocalDate> futureFullMoons = repository.getFutureMoonDates();
        for (LocalDate fullMoonDate : futureFullMoons) {
            if (fullMoonDate.isAfter(date)) {
                break;
            }
            lastFullMoonsRates.add(new Rate(fullMoonDate, getAverageValue(lastFullMoonsRates), currency));
            lastFullMoonsRates.remove(0);
        }

        return new Rate(date, getAverageValue(lastFullMoonsRates), currency);
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
        LocalDate lastDate = null;
        List<Rate> rates = new ArrayList<>();
        switch (period) {
            case WEEK -> lastDate = LocalDate.now().plusDays(7);
            case MONTH -> lastDate = LocalDate.now().plusMonths(1);
        }
        LocalDate currentDate = LocalDate.now().plusDays(1);
        rates.add(getRate(currency, currentDate));
        while (!currentDate.isAfter(lastDate)) {
            currentDate = currentDate.plusDays(1);
            double lastRateValue = rates.get(rates.size() - 1).getRate();
            double randomRate = ThreadLocalRandom.current().nextDouble(lastRateValue * 0.9, lastRateValue * 1.1);
            rates.add(new Rate(currentDate, randomRate, currency));
        }
        return rates;
    }

    private double getAverageValue(List<Rate> rates) {
        return rates.stream()
                .mapToDouble(Rate::getRate)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Отсутствуют данные для расчета средней величины курсов!"));
    }
}
