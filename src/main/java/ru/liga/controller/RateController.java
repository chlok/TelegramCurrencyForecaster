package ru.liga.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.*;
import ru.liga.repository.RatesRepository;
import ru.liga.service.ActualService;
import ru.liga.service.ForecastService;
import ru.liga.service.LinearRegressionService;
import ru.liga.service.MysticService;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс контроллера, обрабатывающего запросы о прогнозе курсов валют
 */

public class RateController implements Controller {

    private final ServiceCommand command;
    private final RatesRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(RateController.class);


    public RateController(ServiceCommand command, RatesRepository repository) {
        this.command = command;
        this.repository = repository;
    }

    /**
     * метод для обработки ввода
     *
     * @return результат обработки ввода
     */
    @Override
    public TelegramCommand operate() {
        ForecastService service = ServiceSelection(command.getAlgorithm(), repository);
        if (command.getPeriod().equals(ForecastPeriod.ONE_DATE)) {
            Rate rate = service.getRate(command.getCurrencies().get(0), command.getDate());
            return new TelegramCommand(rate, command.getOutput(), command.getCurrencies());
        } else {
            List<List<Rate>> ratesList = new ArrayList<>();
            for (Currency currency : command.getCurrencies()) {
                List<Rate> rates = service.getPeriodRates(currency, command.getDate());
                ratesList.add(rates);
            }
            TelegramCommand telegramCommand = new TelegramCommand(ratesList, command.getOutput(), command.getCurrencies());
            logger.info(telegramCommand.toString());
            return telegramCommand;
        }
    }

    private ForecastService ServiceSelection(Algorithm algorithm, RatesRepository repository) {
        return switch (algorithm) {
            case LINEAR_REGRESSION -> new LinearRegressionService(repository);
            case ACTUAL -> new ActualService(repository);
            case MYSTIC -> new MysticService(repository);
        };
    }
}
