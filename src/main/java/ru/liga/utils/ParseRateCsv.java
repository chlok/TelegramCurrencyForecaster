package ru.liga.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Класс для парсинга СSV файлов и маппинга этих данных в модель
 */
public class ParseRateCsv {

    private static final String SEPARATOR = ";";
    private static final Logger logger = LoggerFactory.getLogger(ParseRateCsv.class);

    /**
     * @param filePath путь к файлу для парсинга
     * @throws IOException в случае отсутствия файла или нарушении его структуры будет выброшено исключение
     */


    public static List<Rate> parse(String filePath, Currency currency) throws IOException {
        //Загружаем строки из файла
        List<String> fileLines;
        List<Rate> rateList = new ArrayList<>();
        try (InputStream in = ParseRateCsv.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            fileLines = reader.lines().toList();
        }

        for (int i = 1; i < fileLines.size(); i++) {
            String fileLine = fileLines.get(i);
            String[] splitedText = fileLine.split(SEPARATOR);
            ArrayList<String> columnList = new ArrayList<>();
            for (String s : splitedText) {
                //Если колонка начинается на кавычки или заканчиваеться на кавычки
                if (IsColumnPart(s)) {
                    String lastText = columnList.get(columnList.size() - 1);
                    columnList.set(columnList.size() - 1, lastText + "," + s);
                } else {
                    columnList.add(s);
                }
            }

            //Создаем сущности на основе полученной информации
            LocalDate date = LocalDate.parse(columnList.get(1), DateTimeUtil.PARSE_FORMATTER);
            BigDecimal rateValue = BigDecimal.valueOf(getDoubleFromStringWithComma(columnList.get(2)));
            rateList.add(new Rate(date, rateValue, currency));
        }
        return rateList;
    }

    //Проверка является ли колонка частью предыдущей колонки
    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }

    private static Currency getCurrency(String currencyTitle) {
        Currency currency;
        switch (currencyTitle) {
            case "Доллар США" -> currency = Currency.USD;
            case "ЕВРО" -> currency = Currency.EUR;
            case "Турецкая лира" -> currency = Currency.TRY;
            case "Болгарский лев" -> currency = Currency.BGN;
            case "Армянский драм" -> currency = Currency.AMD;
            default -> currency = null;
        }
        return currency;
    }

    private static double getDoubleFromStringWithComma(String string) {
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number;
        try {
            number = format.parse(string.replaceAll("\"", ""));
        } catch (ParseException e) {
            logger.debug(e.getClass() + " " + e.getMessage());
            throw new IllegalArgumentException("string is not parseable");
        }
        return number.doubleValue();
    }
}
