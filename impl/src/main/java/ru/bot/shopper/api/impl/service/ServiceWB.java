package ru.bot.shopper.api.impl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.bot.shopper.api.impl.repository.OrderRepository;
import ru.bot.shopper.api.model.OrderDto;
import ru.bot.shopper.model.Order;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ServiceWB {

    @Value("${wildberries.token}")
    private String key;

    private OrderRepository orderRepository;

    private RestTemplate restTemplate = new RestTemplate();

//    public ServiceWB(OrderRepository orderRepository) {
////        this.orderRepository = orderRepository;
//    }

    public List<Order> getOrdersByToken(String token){

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity<>(requestHeaders);


        ResponseEntity<OrderDto[]> orders = restTemplate.exchange(
            "https://suppliers-stats.wildberries.ru/api/v1/supplier/orders?dateFrom=2021-10-27T10:04:28Z&flag=1&key="+key,
                HttpMethod.GET,
                requestEntity,
                OrderDto[].class
        );


        Logger.getGlobal().log(Level.INFO, orders.getBody().length + " Количество ордеров");


        List<Order> a= new ArrayList<>();
        return a;
    }
}
