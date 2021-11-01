package ru.bot.shopper.api.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bot.shopper.api.impl.model.Bot;
import ru.bot.shopper.api.impl.service.enums.LoggerLevel;
import ru.bot.shopper.api.model.OrderDto;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class ServiceWB implements BotService {

    @Value("${wildberries.token}")
    private String key;
    @Value("${bot.past-date-minutes}")
    private int pastDateMinutes;

    private Bot bot;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ServiceWB(Bot bot) {
        this.bot = bot;
    }

    public void sendUpdates(List<OrderDto> dtos) {
        dtos.forEach(dto -> bot.sendUpdate(dto));
    }

    public List<OrderDto> getNewOrders(Integer minutesBeforeElapsedTime) {
        if (minutesBeforeElapsedTime == null) {
            minutesBeforeElapsedTime = pastDateMinutes;
        }
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<OrderDto[]> orders = restTemplate.exchange(
                "https://suppliers-stats.wildberries.ru/api/v1/supplier/orders?dateFrom=" + new Date() + "&flag=1&key=" + key,
                HttpMethod.GET,
                requestEntity,
                OrderDto[].class
        );

        List<OrderDto> result = sortOrdersDto(orders.getBody(), minutesBeforeElapsedTime);
        log(ServiceWB.class, LoggerLevel.INFO,
                "getNewOrders()",
                " Orders amount=" + result.size());
        return result;
    }

    private List<OrderDto> sortOrdersDto(OrderDto[] orders, Integer minutesBeforeElapsedTime) {
        ZonedDateTime past = getPastDate(minutesBeforeElapsedTime);
        return Arrays.stream(orders)
                .filter(dto -> dto.getDate().isAfter(past))
                .collect(Collectors.toList());
    }

    private ZonedDateTime getPastDate(Integer minutesBeforeElapsedTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -minutesBeforeElapsedTime);
        ZonedDateTime result = ZonedDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
        return result;
    }
}
