package ru.liga.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.utils.ParseDatesFile;
import ru.liga.utils.ParseRateCsv;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Инмемори репозиторий для хранения значений курсов валют, загруженых из CSV файлов и работы с этими данными
 */

public class InMemoryRatesRepository implements RatesRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryRatesRepository.class);
    private static List<Rate> usd;
    private static List<Rate> euro;
    private static List<Rate> turkey;
    private static List<Rate> armenia;
    private static List<Rate> bulgaria;
    private static List<LocalDate> moonDates;

    static {
        try {
            usd = ParseRateCsv.parse("/USD_F01_02_2005_T05_03_2022.csv");
            euro = ParseRateCsv.parse("/EUR_F01_02_2005_T05_03_2022.csv");
            turkey = ParseRateCsv.parse("/TRY_F01_02_2005_T05_03_2022.csv");
            armenia = ParseRateCsv.parse("/AMD_F01_02_2005_T05_03_2022.csv");
            bulgaria = ParseRateCsv.parse("/BGN_F01_02_2005_T05_03_2022.csv");
            moonDates = ParseDatesFile.parse("/FULL_MOONS_F01_01_2021_T31_12_2022");

        } catch (IOException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
        }
    }

    @Override
    public List<Rate> getAllRates(Currency currency) {
        return switch (currency) {
            case USD -> usd;
            case EUR -> euro;
            case TRY -> turkey;
            case AMD -> armenia;
            case BGN -> bulgaria;
        };
    }


    @Override
    public List<Rate> getPeriodRates(Currency currency, int days) {
        List<Rate> all = getAllRates(currency);
        all = all
                .stream()
                .sorted((x, y) -> y.getDate().compareTo(x.getDate()))
                .limit(days)
                .collect(Collectors.toList());
        return all;
    }


    @Override
    public List<Rate> getTwoAndThreeYearsThisDateRates(Currency currency, LocalDate date) {
        List<Rate> all = getAllRates(currency);
        List<Rate> rates = new ArrayList<>();
        rates.add(getSomeYearsAgoThisDateRate(all, date, 2));
        rates.add(getSomeYearsAgoThisDateRate(all, date, 3));
        return rates;
    }

    private Rate getSomeYearsAgoThisDateRate(List<Rate> rates, LocalDate date, int years){
        List<LocalDate> dates = rates.stream()
                .map(Rate::getDate).toList();
        LocalDate lastDate = date.minusYears(years);
        while (!dates.contains(lastDate)){
            lastDate = lastDate.minusDays(1);
        }
        LocalDate finalDate = lastDate;
        return  rates.stream()
                .filter(x -> (x.getDate().equals(finalDate)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("отсутствуют корректные значения для прогноза в базе данных"));
    }


    @Override
    public List<Rate> getLastFullMoonsRates(Currency currency, int amount) {
        List<Rate> all = getAllRates(currency);
        return all.stream()
                .filter(x -> moonDates.contains(x.getDate()))
                .sorted((x, y) -> y.getDate().compareTo(x.getDate()))
                .limit(amount)
                .collect(Collectors.toList());
    }


    public List<LocalDate> getFutureMoonDates() {
        return moonDates.stream()
                .filter(x -> x.isAfter(LocalDate.now()))
                .sorted()
                .collect(Collectors.toList());
    }
}
