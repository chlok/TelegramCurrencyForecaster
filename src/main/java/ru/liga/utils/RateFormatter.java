package ru.liga.utils;

import ru.liga.model.Rate;

import java.util.List;
import java.util.stream.Collectors;

public class RateFormatter {

    public static String getStringDayRate(Rate rate) {
        return String.format("%s - %s", rate.getDate().format(DateTimeUtil.PRINT_FORMATTER), String.format("%.2f", rate.getRate()));
    }


    public static String getStringWeekRate(List<Rate> rates) {
        return rates.stream()
                .map(RateFormatter::getStringDayRate)
                .collect(Collectors.joining("\n"));
    }


}
