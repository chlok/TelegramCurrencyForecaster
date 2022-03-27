package ru.liga.model;

import java.time.LocalDate;
import java.util.List;

public class ServiceCommand {
    Output output;
    LocalDate date;
    Algorithm algorithm;
    List<Currency> currencies;
    ForecastPeriod period;

    public ServiceCommand(Output output, LocalDate date, Algorithm algorithm, List<Currency> currencies, ForecastPeriod period) {
        this.output = output;
        this.date = date;
        this.algorithm = algorithm;
        this.currencies = currencies;
        this.period = period;
    }

    public Output getOutput() {
        return output;
    }


    public LocalDate getDate() {
        return date;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public ForecastPeriod getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return "ServiceCommand{" +
                "output=" + output +
                ", date=" + date +
                ", algorithm=" + algorithm +
                ", currencies=" + currencies +
                ", period=" + period +
                '}';
    }
}
