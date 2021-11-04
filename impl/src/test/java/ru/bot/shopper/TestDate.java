package ru.bot.shopper;

import org.junit.jupiter.api.Test;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class TestDate {

    @Test
    public void testDate(){
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        System.out.println(ZonedDateTime.now().withZoneSameLocal(ZoneId.of("Europe/Moscow")).minus(15, ChronoUnit.MINUTES).format(sdf));
    }

    @Test
    public void testDate2(){
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        System.out.println(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).minus(15, ChronoUnit.MINUTES).format(sdf));

        System.out.println(" --- " + "".isEmpty());
    }
}
