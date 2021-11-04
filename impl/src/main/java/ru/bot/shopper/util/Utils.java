package ru.bot.shopper.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utils {

    private static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final DateTimeFormatter hm = DateTimeFormatter.ofPattern("HH:mm:ss");


    public static String convertDateToString(ZonedDateTime dateTime){
        return dateTime.format(sdf);
    }

    public static ZonedDateTime getDateTime(Integer minutes){
        if(minutes == null) minutes = 15;
        return ZonedDateTime
                .now(ZoneId.of("Europe/Moscow"))
                .minus(minutes, ChronoUnit.MINUTES);

    }

    public static String getHourMinuteNow(){
        return ZonedDateTime
                .now(ZoneId.of("Europe/Moscow"))
                .format(hm);

    }

    public static HttpEntity createHeaders(){
        return new HttpEntity<>(new HttpHeaders());
    }

    public static String createUrl(String url, ZonedDateTime time, String flag, String key){
        return url + convertDateToString(time) + "&flag=" + flag + "&key=" + key;
    }

    public static String getChartId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    public static String getText(Update update) {
        return update.getMessage().getText().trim();
    }

    public static Integer getCountMinutes(String text){
        return Integer.parseInt(text.split("\\s+")[1]);
    }

}
