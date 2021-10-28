package ru.bot.shopper.api.impl.service.enums;

public enum LoggerValue {
    TEST_VALUE(" -> test description.")
    ;
    public String description;

    LoggerValue(String description) {
        this.description = description;
    }
}
