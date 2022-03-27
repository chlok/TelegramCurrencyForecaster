package ru.liga;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestRepository implements RatesRepository {

    public final List<Rate> rates = new ArrayList<>();

    public TestRepository() {
        rates.add(new Rate(LocalDate.of(2022, 3, 27), BigDecimal.valueOf(101), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 26), BigDecimal.valueOf(100), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 25), BigDecimal.valueOf(99), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 24), BigDecimal.valueOf(98), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 23), BigDecimal.valueOf(97), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 22), BigDecimal.valueOf(96), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 21), BigDecimal.valueOf(95), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 20), BigDecimal.valueOf(94), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 19), BigDecimal.valueOf(93), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 18), BigDecimal.valueOf(92), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 17), BigDecimal.valueOf(91), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 16), BigDecimal.valueOf(90), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 15), BigDecimal.valueOf(89), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 14), BigDecimal.valueOf(88), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 13), BigDecimal.valueOf(87), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 12), BigDecimal.valueOf(86), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 11), BigDecimal.valueOf(85), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 10), BigDecimal.valueOf(84), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 9), BigDecimal.valueOf(83), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 8), BigDecimal.valueOf(82), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 7), BigDecimal.valueOf(81), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 6), BigDecimal.valueOf(80), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 5), BigDecimal.valueOf(79), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 4), BigDecimal.valueOf(78), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 3), BigDecimal.valueOf(77), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 2), BigDecimal.valueOf(76), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 3, 1), BigDecimal.valueOf(75), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 28), BigDecimal.valueOf(74), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 27), BigDecimal.valueOf(73), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 26), BigDecimal.valueOf(72), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 25), BigDecimal.valueOf(71), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 24), BigDecimal.valueOf(70), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 23), BigDecimal.valueOf(69), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 22), BigDecimal.valueOf(68), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 21), BigDecimal.valueOf(67), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 20), BigDecimal.valueOf(66), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2022, 2, 20), BigDecimal.valueOf(65), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2020, 3, 30), BigDecimal.valueOf(65), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2019, 3, 30), BigDecimal.valueOf(65), Currency.BGN));
    }

    @Override
    public List<Rate> getAllRates(Currency currency) {
        return rates;
    }

    @Override
    public List<Rate> getPeriodRates(Currency currency, int days) {
        return rates.subList(0, days);
    }

    @Override
    public List<Rate> getTwoAndThreeYearsThisDateRates(Currency currency, LocalDate date) {
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate(LocalDate.of(2022, 2, 20), BigDecimal.valueOf(65), Currency.BGN));
        rates.add(new Rate(LocalDate.of(2020, 3, 30), BigDecimal.valueOf(65), Currency.BGN));
        return rates;
    }


    @Override
    public List<Rate> getLastFullMoonsRates(Currency currency, int amount) {
        List<Rate> lastFullMoonsRates = new ArrayList<>();
        lastFullMoonsRates.add(new Rate(LocalDate.of(2022, 2, 20), BigDecimal.valueOf(20), Currency.BGN));
        lastFullMoonsRates.add(new Rate(LocalDate.of(2020, 3, 30), BigDecimal.valueOf(30), Currency.BGN));
        lastFullMoonsRates.add(new Rate(LocalDate.of(2019, 3, 25), BigDecimal.valueOf(40), Currency.BGN));
        return lastFullMoonsRates;
    }

    @Override
    public List<LocalDate> getFutureMoonDates() {
        List<LocalDate> futureMoonDates = new ArrayList<>();
        futureMoonDates.add(LocalDate.of(2022, 4, 16));
        futureMoonDates.add(LocalDate.of(2022, 5, 16));
        futureMoonDates.add(LocalDate.of(2022, 6, 14));
        futureMoonDates.add(LocalDate.of(2022, 7, 13));
        futureMoonDates.add(LocalDate.of(2022, 8, 12));
        futureMoonDates.add(LocalDate.of(2022, 9, 10));
        futureMoonDates.add(LocalDate.of(2022, 10, 9));
        futureMoonDates.add(LocalDate.of(2022, 11, 8));
        futureMoonDates.add(LocalDate.of(2022, 12, 8));
        return futureMoonDates;
    }
}
