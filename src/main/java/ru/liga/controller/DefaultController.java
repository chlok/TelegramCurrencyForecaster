package ru.liga.controller;

import ru.liga.model.TelegramCommand;

/**
 * Класс контроллера для обработки команд непредусмотрееных приложением
 */
public class DefaultController implements Controller {
    private final String command;

    public DefaultController(String command) {
        this.command = command;
    }

    /**
     * Метод для обработки некорректного ввода
     *
     * @return строка, сообщающая о некорректном вводе
     */
    @Override
    public TelegramCommand operate() {
        return new TelegramCommand("Введена неверная команда: " + "\"" + command + "\"" +
                "Для просмотра списка доступных команд введите \"help\"");
    }
}
