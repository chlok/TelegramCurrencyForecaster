package ru.liga.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс описывоющий модель объекта Rate, хранящего информацию о курсе валюты в определенную дату
 */

public class Rate {
    private final LocalDate date;
    private final BigDecimal rate;
    private final Currency currency;

    public Rate(LocalDate dateTime, BigDecimal rate, Currency currency) {
        this.date = dateTime;
        this.currency = currency;
        this.rate = rate.setScale(3, RoundingMode.HALF_UP);
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Rate{" + "date=" + date + ", rate=" + rate + ", title='" + currency + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate1 = (Rate) o;
        return Objects.equals(date, rate1.date) && Objects.equals(rate, rate1.rate) && currency == rate1.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rate, currency);
    }
}
