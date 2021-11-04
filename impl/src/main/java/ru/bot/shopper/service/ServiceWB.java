package ru.bot.shopper.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bot.shopper.Bot;
import ru.bot.shopper.OrderDto;
import ru.bot.shopper.service.enums.LoggerLevel;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bot.shopper.util.Utils.*;

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

    private final RestTemplate restTemplate = new RestTemplate();

    public String getNewOrders(Integer... minutesBeforeElapsedTime) {
        Integer minutes;
        if (minutesBeforeElapsedTime.length == 0) minutes = pastDateMinutes;
        else minutes = minutesBeforeElapsedTime[0];

        ZonedDateTime dateNow = getDateTime(minutes);

        log(ServiceWB.class, LoggerLevel.INFO, "getNewOrders()", "Start getNewOrders time from " + dateNow);
        OrderDto[] orders = requestWB(dateNow);

        if (orders != null) {
            List<OrderDto> result = Arrays
                    .stream(orders)
                    .filter(orderDto -> orderDto.getDate().isAfter(dateNow))
                    .sorted(Comparator.comparingInt(dto -> dto.getDate().getMinute()))
                    .collect(Collectors.toList());

            log(ServiceWB.class, LoggerLevel.INFO, "getNewOrders()", "Finish orders amount=" + result.size());
            return joinOrders(result, convertDateToString(dateNow));
        }
        return "Нет продаж!! Работай усерднее";

    }

    private OrderDto[] requestWB(ZonedDateTime dateNow){
        return restTemplate.exchange(createUrl(url, dateNow, flag, key),
                HttpMethod.GET,
                createHeaders(),
                OrderDto[].class
        ).getBody();
    }

    private String joinOrders(List<OrderDto> result, String timeStart) {
        StringBuilder b = new StringBuilder();
        result.forEach(b::append);
        if (!result.isEmpty()) {
            b.insert(1, "Ой, смотри сколько ты наторговал в период с " + timeStart + " по " + getHourMinuteNow() + "\n\n");
        }
        return b.toString();
    }


}
