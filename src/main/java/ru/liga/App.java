package ru.liga;

import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.view.Bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    private static final String propertyPath = "src/main/resources/TelegramBot.properties";
    public static final String BOT_USER_NAME = "bot.user_name";
    public static final String BOT_TOKEN = "bot.token";
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Properties properties = new Properties();
            properties.load(new FileInputStream(propertyPath));
            String userName = properties.getProperty(BOT_USER_NAME);
            String token = properties.getProperty(BOT_TOKEN);
            botsApi.registerBot(new Bot(userName, token));
        } catch (TelegramApiException | IOException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
        }
    }
}

