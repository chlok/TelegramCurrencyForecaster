package ru.liga.controller;

import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RatesRepository;
import ru.liga.service.ActualService;
import ru.liga.service.ForecastService;
import ru.liga.service.LinearRegressionService;
import ru.liga.service.MysticService;
import ru.liga.utils.ForecastPeriod;
import ru.liga.utils.Graph;
import ru.liga.utils.RateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.liga.utils.DateTimeUtil.PARSE_FORMATTER;

/**
 * Класс контроллера, обрабатывающего запросы о прогнозе курсов валют
 */

public class RateController implements Controller {

    private final String[] commands;
    private final RatesRepository repository;


    public RateController(String[] commands, RatesRepository repository) {
        this.commands = commands;
        this.repository = repository;
    }

    @Override
    public String operate() {
        if (!commands[0].equalsIgnoreCase("rate")) {
            return "команда \"rate\" введена некорректно!";
        }

        if (!((commands[2].equalsIgnoreCase("-date") && commands.length == 6) ||
                (commands[2].equalsIgnoreCase("-period") && commands.length == 8))) {
            return "команда для выбора даты выбрана некорректно! верные команды - \"-date\" и \"-period\"";
        }

        if (!commands[4].equalsIgnoreCase("-alg")) {
            return "команда для выбора даты выбрана некорректно! верная команда - \"-alg\"";
        }

        if (commands.length == 8 && !commands[6].equalsIgnoreCase("-output")) {
            return "команда для выбора формата вывода выбрана некорректно! верная команда - \"-output\"";
        }


        ForecastService service = getForecastService();
        if (service == null) {
            return "such forecast algorithm doesn't exist";
        }

        switch (commands.length) {
            case 6 -> {
                return operateDateForecast(service);
            }
            case 8 -> {
                return operatePeriodForecast(service);
            }
            default -> {
                return ("После команды \"rate\" необходимо вводить наименование валюты и период прогноза.\n" +
                        "Для просмотра списка доступных команд введите \"help\"");
            }

        }
    }

    private String operateDateForecast(ForecastService service) {
        LocalDate date;
        Currency currency;
        try {
            date = getDate(commands[3]);
            currency = getCurrency(commands[1]);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        Rate rate = service.getRate(currency, date);
        return RateFormatter.getStringDayRate(rate);
    }

    private String operatePeriodForecast(ForecastService service) {
        ForecastPeriod forecastPeriod;
        try {
            forecastPeriod = getForecastPeriod(commands[3]);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        List<List<Rate>> ratesLists = new ArrayList<>();
        for (String string : commands[1].split(",")) {
            Currency currency;
            try {
                currency = getCurrency(string);
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }
            List<Rate> rates = service.getPeriodRates(currency, forecastPeriod);
            ratesLists.add(rates);
        }
        switch (commands[7]) {
            case "list" -> {
                return RateFormatter.getStringWeekRate(ratesLists.get(0));
            }
          //  case "graph" -> {return  new Graph().drawGraph(ratesLists, forecastPeriod);};
            default -> {
                return "выбранный формат вывода отсутсвует в приложении!";
            }
        }
    }

    private ForecastPeriod getForecastPeriod(String period) {
        try {
            return ForecastPeriod.valueOf(period.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("введен некорректный период для прогноза!");
        }

    }

    private ForecastService getForecastService() {
        ForecastService service = null;
        switch (commands[5]) {
            case "moon" -> service = new MysticService(repository);
            case "actual" -> service = new ActualService(repository);
            case "regression:" -> service = new LinearRegressionService(repository);
        }
        return service;
    }

    private LocalDate getDate(String string) {
        LocalDate date;
        if (string.equalsIgnoreCase("tomorrow")) {
            date = LocalDate.now().plusDays(1);
        } else {
            try {
                date = LocalDate.parse(string, PARSE_FORMATTER);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("введена некорректная дата. Дату необходимо ввести в формате: d.MM.yyyy");
            }
            if (date.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("выбранная дата для прогноза уже прошла!");
            }
        }
        return date;
    }

    private Currency getCurrency(String string) {
        try {
            return Currency.valueOf(string.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("После команды \"rate\" необходимо вводить сокращенное наименование валюты (USD, EUR, TRY)\n + " +
                    "или несколько таких названий через \",\"(USD,EUR)");
        }
    }
}
