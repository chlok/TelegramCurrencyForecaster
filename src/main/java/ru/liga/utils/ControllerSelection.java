package ru.liga.utils;

import ru.liga.controller.Controller;
import ru.liga.controller.DefaultController;
import ru.liga.controller.RateController;
import ru.liga.controller.SystemController;
import ru.liga.repository.InMemoryRatesRepository;

import java.util.Locale;

/**
 * Класс отвечающий за выбор контроллера, который будет работать с поступившей командой
 *
 */
public class ControllerSelection {
    private static final String separator = " ";

    public static Controller getController(String command) {
        String[] commands = command.split(separator);

        return switch (commands[0].toLowerCase(Locale.ROOT)) {
            case "help", "exit" -> new SystemController(commands[0]);
            case "rate" -> new RateController(commands, new InMemoryRatesRepository());
            default -> new DefaultController(command);
        };
    }
}
