package ru.liga.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.model.Rate;
import ru.liga.model.TelegramCommand;
import ru.liga.utils.ControllerSelection;
import ru.liga.utils.Graph;
import ru.liga.utils.RateFormatter;

import java.io.File;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    public Bot(String botName, String botToken) {
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * метод для обработки команд в телеграмме
     *
     * @param update сообщение от пользователя
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            logger.info(update.toString());
            TelegramCommand answer = ControllerSelection.getController(update.getMessage().getText()).operate();
            setAnswer(update.getMessage().getChatId(), answer);
        } catch (IllegalArgumentException e) {
            sendTextMessage(update.getMessage().getChatId(), e.getMessage());
            logger.debug(e.getClass() + e.getMessage());
        }
    }


    /**
     * Формирование имени пользователя
     *
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     *
     * @param chatId  id чата
     * @param command команда для выполнения
     */
    private void setAnswer(Long chatId, TelegramCommand command) {
        switch (command.getOutput()) {
            case MESSAGE -> sendTextMessage(chatId, command.getMessage());
            case DATE -> sendTextMessage(chatId, RateFormatter.getStringDayRate(command.getRate()));
            case LIST -> sendTextMessage(chatId, RateFormatter.getStringWeekRate(command.getRatesList().get(0)));
            case GRAPH -> sendImage(chatId, command.getRatesList());
        }
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage answer = new SendMessage();
        if (text != null) {
            answer.setText(text);
            answer.setChatId(chatId.toString());
            try {
                execute(answer);
                logger.info(answer.toString());
            } catch (TelegramApiException e) {
                logger.debug(e.getClass() + e.getMessage());
            }
        }
    }

    private void sendImage(Long chatId, List<List<Rate>> rateList) {
        SendPhoto answer = new SendPhoto();
        Graph graph = new Graph();
        graph.drawGraph(rateList);
        answer.setPhoto(new InputFile(new File(graph.FILE_NAME)));
        answer.setChatId(String.valueOf(chatId));
        answer.setCaption("");
        try {
            execute(answer);
            logger.info(answer.toString());
        } catch (TelegramApiException e) {
            logger.debug(e.getClass() + e.getMessage());
        }
    }
}
