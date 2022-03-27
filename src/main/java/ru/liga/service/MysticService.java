package ru.liga.service;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.model.ForecastPeriod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MysticService implements ForecastService {
    private final RatesRepository repository;

    public MysticService(RatesRepository repository) {
        this.repository = repository;
    }

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

    @Override
    public List<Rate> getPeriodRates(Currency currency, LocalDate lastDate) {
        List<Rate> rates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now().plusDays(1);
        rates.add(getRate(currency, currentDate));
        while (!currentDate.isAfter(lastDate)) {
            currentDate = currentDate.plusDays(1);
            BigDecimal lastRateValue = rates.get(rates.size() - 1).getRate();
            double randomValue = ThreadLocalRandom.current().nextDouble(0.9,1.1);
            BigDecimal randomRate = lastRateValue.multiply(BigDecimal.valueOf(randomValue));
            rates.add(new Rate(currentDate, randomRate, currency));
        }
        return rates;
    }

    private BigDecimal getAverageValue(List<Rate> rates) {
        BigDecimal sum = new BigDecimal(0);
        for (Rate rate:rates){
            sum = sum.add(rate.getRate());
        }
        return sum.divide(new BigDecimal(rates.size()), 3, RoundingMode.HALF_UP);
    }
}
