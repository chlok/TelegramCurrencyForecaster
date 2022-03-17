package ru.liga.utils;

import ru.liga.model.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Класс для парсинга текстовых файлов c датами
 */
public class ParseDatesFile {


    /**
     * @param filePath путь к файлу для парсинга
     * @throws IOException в случае отсутствия файла или нарушении его структуры будет выброшено исключение
     */


    public static List<LocalDate> parse(String filePath) throws IOException {
        //Загружаем строки из файла
        List<String> fileLines;
        List<Rate> rateList = new ArrayList<>();
        try (InputStream in = ParseDatesFile.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return reader.lines()
                    .map(x -> LocalDate.parse(x, DateTimeUtil.PARSE_FORMATTER))
                    .collect(Collectors.toList());
        }
    }
}
