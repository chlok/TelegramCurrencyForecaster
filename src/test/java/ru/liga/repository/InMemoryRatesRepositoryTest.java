package ru.liga.repository;

import org.junit.Assert;
import org.junit.Test;
import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryRatesRepositoryTest {
    RatesRepository repository = new InMemoryRatesRepository();

    @Test
    public void getPeriodRatesTest() {
        List<Rate> rates = repository.getPeriodRates(Currency.EUR, 2);
        List<Rate> correctRates = new ArrayList<>();
        correctRates.add(new Rate(LocalDate.of(2022, 3,27), BigDecimal.valueOf(116), Currency.EUR));
        correctRates.add(new Rate(LocalDate.of(2022, 3,26), BigDecimal.valueOf(116), Currency.EUR));
        Assert.assertEquals(rates, correctRates);
    }

    @Test
    public void getTwoAndThreeYearsThisDateRatesTest(){
        List<Rate> rates = repository.getTwoAndThreeYearsThisDateRates(Currency.EUR, LocalDate.of(2022, 3,30));
        List<Rate> correctRates = new ArrayList<>();
        correctRates.add(new Rate(LocalDate.of(2020, 3,28), BigDecimal.valueOf(85.7389), Currency.EUR));
        correctRates.add(new Rate(LocalDate.of(2019, 3,30), BigDecimal.valueOf(72.723), Currency.EUR));
        Assert.assertEquals(rates, correctRates);
    }


}
