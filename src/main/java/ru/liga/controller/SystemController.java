package ru.liga.controller;

import ru.liga.model.TelegramCommand;

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
    public TelegramCommand operate() {
        switch (command.toLowerCase(Locale.ROOT)) {
            case "help" -> {
                return new TelegramCommand("""
                        Cписок доступных комманд:
                                                
                        \thelp     - помощь
                        \trate     - прогноз курсов валют, с этой командой необходимо указывать одну или 
                        \tнесколько валют (USD, EUR, TRY, AMD, BGN, несколько - только в случае выбора периода),
                        \tпериод(week, month), алгоритм(moon, regression, actual), при выборе периода - формат
                        \tвывода (list, graph)
                        \tПримеры запросов:
                        \trate TRY -date tomorrow -alg moon
                        \trate TRY -date 22.02.2030 -alg moon
                        \trate USD -period week -alg moon -output list
                        \trate USD,TRY -period month -alg moon -output graph
                        \tа так же период прогноза
                                                
                        \texit     - завершение программы""");
            }
            case "exit" -> {
                System.exit(0);
                return new TelegramCommand("Exit");
            }
            default -> {
                return new DefaultController(command).operate();
            }
        }
    }
}
