package ru.bot.shopper.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bot.shopper.Bot;
import ru.bot.shopper.OrderDto;
import ru.bot.shopper.service.enums.LoggerLevel;
import ru.bot.shopper.util.Utils;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceWB implements BotService {

    @Value("${wildberries.token}")
    private String key;

    @Value("${wildberries.url}")
    private String url;

    @Value("${wildberries.flag}")
    private String flag;

    @Value("${bot.past-date-minutes}")
    private int pastDateMinutes;

    private final Bot bot;

    private final RestTemplate restTemplate = new RestTemplate();

    public ServiceWB(Bot bot) {
        this.bot = bot;
    }

    public String getNewOrders(Integer minutesBeforeElapsedTime) {

        if (minutesBeforeElapsedTime == null) minutesBeforeElapsedTime = pastDateMinutes;

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<OrderDto[]> orders =
                restTemplate.exchange(url + Utils.getDateTime(minutesBeforeElapsedTime) + "&flag=" +flag + "&key=" + key,
                HttpMethod.GET,
                requestEntity,
                OrderDto[].class
        );

        if(orders.getBody() != null){
            Integer finalMinutesBeforeElapsedTime = minutesBeforeElapsedTime;
            List<OrderDto> result = Arrays.stream(orders.getBody())
                    .filter(orderDto -> orderDto.getDate().isAfter(ZonedDateTime.now().minus(finalMinutesBeforeElapsedTime, ChronoUnit.MINUTES)))
                    .sorted(Comparator.comparingInt(dto -> dto.getDate().getMinute()))
                    .collect(Collectors.toList());

            log(ServiceWB.class, LoggerLevel.INFO,
                    "getNewOrders()",
                    " Orders amount=" + result.size());
            return joinOrders(result, minutesBeforeElapsedTime);
        }

        return "Нет продаж!! Работай усерднее";

    }

    private String joinOrders(List<OrderDto> result, Integer minutesBeforeElapsedTime){
        StringBuilder b = new StringBuilder();
        result.forEach(b::append);
        if(result.size() == 0) b.append("В период с " + Utils.getHourMinuteNowLess(minutesBeforeElapsedTime) + " по " + Utils.getHourMinuteNow() +" Нет продаж!! Работай усерднее");
        else b.insert(1, "Ой, смотри сколько ты наторговал в период с " + Utils.getHourMinuteNowLess(minutesBeforeElapsedTime) + " по " + Utils.getHourMinuteNow() +"\n\n");
        return b.toString();
    }


}
