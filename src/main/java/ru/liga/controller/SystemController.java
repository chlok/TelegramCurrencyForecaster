package ru.liga.controller;

import java.util.Locale;

/**
 * Класс контроллера обрабатывающий команды системного характера
 */

public class SystemController implements Controller {
    private final String command;

    public SystemController(String command) {
        this.command = command;
    }

    @Override
    public String operate() {
        switch (command.toLowerCase(Locale.ROOT)) {
            case "help" -> {
                return """
                        Cписок доступных комманд:
                        \thelp     - помощь
                        \trate     - прогноз курсов валют, с этой командой необходимо указывать валюту (USD, EUR, TRY),
                        \tа так же период прогноза, на 1 день или на неделю (tomorrow или week)
                        \tпараметры команды необходимо указывать через пробел (пример: rate TRY tomorrow)
                        \texit     - завершение программы""";
            }
            case "exit" -> {
                System.exit(0);
                return "Exit";
            }
            default -> {
                return new DefaultController(command).operate();
            }
        }
    }
}
