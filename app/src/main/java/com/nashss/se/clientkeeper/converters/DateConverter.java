package com.nashss.se.clientkeeper.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter implements DynamoDBTypeConverter<String, LocalDate> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String convert(LocalDate date) {
        return date.format(FORMATTER);
    }

    @Override
    public LocalDate unconvert(String string) {
        return LocalDate.parse(string, FORMATTER);
    }
}

