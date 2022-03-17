package ru.liga.utils;

import ru.liga.model.Rate;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RateFormatter {
    private final Scanner scanner;

    public RateFormatter() {
        scanner = new Scanner(System.in);
    }

    public String insertCommand() {
        System.out.print("Введите команду: ");
        return scanner.nextLine();
    }

    public void printMessage(String text) {
        System.out.println(text);
    }

    public static String getStringDayRate(Rate rate) {
        return String.format("%s - %s", rate.getDate().format(DateTimeUtil.PRINT_FORMATTER), String.format("%.2f", rate.getRate()));
    }


    public static String getStringWeekRate(List<Rate> rates) {
        return rates.stream()
                .map(RateFormatter::getStringDayRate)
                .collect(Collectors.joining("\n"));
    }


}
