package ru.liga.model;

import java.util.List;

public class TelegramCommand {
    private final List<List<Rate>> ratesList;
    private final Rate rate;
    private final Output output;
    List<Currency> currencies;
    String message;

    public TelegramCommand(List<List<Rate>> ratesList, Output output, List<Currency> currencies) {
        this.ratesList = ratesList;
        this.output = output;
        this.rate = null;
        this.currencies = currencies;
    }

    public TelegramCommand(Rate rate, Output output, List<Currency> currencies) {
        this.rate = rate;
        this.output = output;
        this.ratesList = null;
        this.currencies = currencies;
    }

    public TelegramCommand(String message) {
        this.message = message;
        this.output = Output.MESSAGE;
        ratesList = null;
        rate = null;
    }

    public List<List<Rate>> getRatesList() {
        return ratesList;
    }

    public Rate getRate() {
        return rate;
    }

    public Output getOutput() {
        return output;
    }

    public String getMessage() {
        return message;
    }
}


