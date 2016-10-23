package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class FormatToDate implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return TimeUtil.parseLocalDate(text);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
