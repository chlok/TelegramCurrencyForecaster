package ru.liga.service;

import org.junit.Assert;
import org.junit.Test;
import ru.liga.TestRepository;
import ru.liga.model.Currency;
import ru.liga.model.Rate;

import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LinearRegressionServiceTest {
    ForecastService service = new LinearRegressionService(new TestRepository());

    @Test
    public void getRateTest(){
        Rate rate = service.getRate(Currency.BGN, LocalDate.of(2022, 3,30));
        Assert.assertEquals(rate,
                new Rate(LocalDate.of(2022, 3,30), BigDecimal.valueOf(104), Currency.BGN));
    }
}
