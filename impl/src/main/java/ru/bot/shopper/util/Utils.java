package ru.bot.shopper.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utils {

    private static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final DateTimeFormatter hm = DateTimeFormatter.ofPattern("HH:mm:ss");


    public static String getDateTime(Integer minutes){
        if(minutes == null) minutes = 15;
        return ZonedDateTime
                .now()
                .withZoneSameLocal(ZoneId.of("Europe/Moscow"))
                .minus(minutes, ChronoUnit.MINUTES)
                .format(sdf);
    }

    public static String getHourMinuteNow(){
        return ZonedDateTime
                .now()
                .withZoneSameLocal(ZoneId.of("Europe/Moscow"))
                .format(hm);

    }

    public static String getHourMinuteNowLess(Integer minutes){
        return ZonedDateTime
                .now()
                .withZoneSameLocal(ZoneId.of("Europe/Moscow"))
                .minus(minutes, ChronoUnit.MINUTES)
                .format(hm);

    }
}
