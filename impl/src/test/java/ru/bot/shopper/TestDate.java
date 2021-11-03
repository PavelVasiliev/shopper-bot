package ru.bot.shopper;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class TestDate {

    @Test
    public void testDate(){
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        System.out.println(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("Europe/Moscow")).minus(15, ChronoUnit.MINUTES).format(sdf));
    }
}
