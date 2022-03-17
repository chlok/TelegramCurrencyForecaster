package ru.liga.controller;

/**
 * Класс контроллера для обработки команд непредусмотрееных приложением
 */
public class DefaultController implements Controller {
    private final String command;

    public DefaultController(String command) {
        this.command = command;
    }

    @Override
    public String operate() {
        return "Введена неверная команда: " + "\"" + command + "\"" +
                "Для просмотра списка доступных команд введите \"help\"";
    }
}
