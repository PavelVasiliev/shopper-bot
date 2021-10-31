package ru.bot.shopper.api.impl.service;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bot.shopper.api.impl.mapper.OrderMapper;
import ru.bot.shopper.api.impl.repository.OrderRepository;
import ru.bot.shopper.api.impl.service.enums.LoggerLevel;
import ru.bot.shopper.api.model.OrderDto;
import ru.bot.shopper.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class ServiceWB implements BotService {

    @Value("${wildberries.token}")
    private String key;

    private OrderRepository orderRepository;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ServiceWB(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersByToken(String token) {

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<OrderDto[]> orders = restTemplate.exchange(
                "https://suppliers-stats.wildberries.ru/api/v1/supplier/orders?dateFrom=2021-10-27T10:04:28Z&flag=1&key=" + key,
                HttpMethod.GET,
                requestEntity,
                OrderDto[].class
        );


        //Logger.getGlobal().log(Level.INFO, orders.getBody().length + " Количество ордеров");
        log(ServiceWB.class, LoggerLevel.INFO,
                "getOrdersByToken()",
                orders.getBody().length + " Количество ордеров");

        return ordersFromDTOs(orders.getBody());
    }

    public List<Order> getOrdersByDialogId(String dialogId) {

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);

        String date = getPastDate();
        ResponseEntity<OrderDto[]> orders = restTemplate.exchange(
                "https://suppliers-stats.wildberries.ru/api/v1/supplier/orders?dateFrom="+ date + "&flag=1&key=" + key,
                HttpMethod.GET,
                requestEntity,
                OrderDto[].class
        );


        //Logger.getGlobal().log(Level.INFO, orders.getBody().length + " Количество ордеров");
        log(ServiceWB.class, LoggerLevel.INFO,
                "getOrdersByToken()",
                orders.getBody().length + " Количество ордеров");

        return ordersFromDTOs(orders.getBody());
    }

    private String getPastDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -15);
        return sdf.format(cal.getTime());
    }

    private List<Order> ordersFromDTOs(OrderDto[] dtos) {
        List<Order> result = new ArrayList<>();
        for (OrderDto dto : dtos) {
            result.add(orderMapper.toEntity(dto));
        }
        orderRepository.saveAll(result);
        return result;
    }
}
