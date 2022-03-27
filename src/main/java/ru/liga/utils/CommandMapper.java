package ru.liga.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandMapper {
    Algorithm algorithm;
    Output output;
    ForecastPeriod forecastPeriod;
    LocalDate date;
    List<Currency> currencies = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandMapper.class);

    public ServiceCommand mapCommandFromStringArray(String[] strings) {
        checkCommandNaming(strings);
        setAlgorithm(strings[5]);
        setOutput(strings);
        getCurrencies(strings[1]);
        setPeriodAndDate(strings);
        ServiceCommand serviceCommand = new ServiceCommand(output, date, algorithm, currencies, forecastPeriod);
        logger.info(serviceCommand.toString());
        return serviceCommand;
    }

    private void getCurrencies(String string) {
        String currencySeparator = ",";
        for (String cur : string.split(currencySeparator)) {
            currencies.add(getCurrency(cur));
        }
    }

    private Currency getCurrency(String string) {
        try {
            return Currency.valueOf(string.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
            throw new IllegalArgumentException("После команды \"rate\" необходимо вводить сокращенное наименование валюты (USD, EUR, TRY)\n + " + "или несколько таких названий через \",\"(USD,EUR)");
        }
    }

    private void setPeriodAndDate(String[] strings) {
        if (strings[2].equalsIgnoreCase("-date")) {
            forecastPeriod = ForecastPeriod.ONE_DATE;
            if (strings[3].equalsIgnoreCase("tomorrow")) {
                date = LocalDate.now().plusDays(1);
            } else {
                date = parseDate(strings[3]);
            }
        } else {
            switch (strings[3]) {
                case "week" -> {
                    date = LocalDate.now().plusDays(7);
                    forecastPeriod = ForecastPeriod.WEEK;
                }
                case "month" -> {
                    date = LocalDate.now().plusMonths(1);
                    forecastPeriod = ForecastPeriod.MONTH;
                }
                default -> throw new IllegalArgumentException("введен некорректный период прогнозирования");
            }
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("введенная дата для прогнозирования уже прошла!");
        }
    }

    private LocalDate parseDate(String string) {
        try {
            return LocalDate.parse(string, DateTimeUtil.PARSE_FORMATTER);
        } catch (DateTimeParseException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
            throw new IllegalArgumentException("введена некорретная дата");
        }
    }

    private void setOutput(String[] strings) {
        if (strings[2].equalsIgnoreCase("-date")) {
            output = Output.DATE;
        } else {
            try {
                output = Output.valueOf(strings[7].toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                logger.debug(e.getClass() + " " + e.getMessage());
                throw new IllegalArgumentException("введен некорректный формат периода прогнозирования");
            }
        }
    }

    private void setAlgorithm(String string) {
        switch (string) {
            case "moon" -> {
                algorithm = Algorithm.MYSTIC;
            }
            case "actual" -> {
                algorithm = Algorithm.ACTUAL;
            }
            case "regression" -> {
                algorithm = Algorithm.LINEAR_REGRESSION;
            }
            default -> throw new IllegalArgumentException("выбранный алгоритм прогноза не сущестует!");
        }
    }

    private void checkCommandNaming(String[] strings) {
        if (!strings[0].equalsIgnoreCase("rate")) {
            throw new IllegalArgumentException("команда \"rate\" введена некорректно!");
        }

        if (!(strings.length == 6 || strings.length == 8)) {
            throw new IllegalArgumentException("команда составлена некорректно! " + "некорректное количество составляющих");
        }

        if (!((strings[2].equalsIgnoreCase("-date") && strings.length == 6) || (strings[2].equalsIgnoreCase("-period") && strings.length == 8))) {
            throw new IllegalArgumentException("команда для выбора даты выбрана некорректно! " + "верные команды - \"-date\" и \"-period\"");
        }

        if (!strings[4].equalsIgnoreCase("-alg")) {
            throw new IllegalArgumentException("команда для выбора даты выбрана некорректно! верная команда - \"-alg\"");
        }

        if (strings.length == 8 && !strings[6].equalsIgnoreCase("-output")) {
            throw new IllegalArgumentException("команда для выбора формата вывода выбрана некорректно! " + "верная команда - \"-output\"");
        }
    }
}
