package ru.bot.shopper.api.impl.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import ru.bot.shopper.api.impl.service.ServiceWB;
import ru.bot.shopper.api.model.OrderDto;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@RestController
public class TestController {

    private ServiceWB serviceWB;

    @Autowired
    public TestController(ServiceWB serviceWB) {
        this.serviceWB = serviceWB;
    }

    @Scheduled(cron = "1 */15 * * * *") //every 15 mins
    public ResponseEntity sendUpdatesScheduled() {
        List<OrderDto> orders = serviceWB.getNewOrders(null);
        serviceWB.sendUpdates(orders);
        return ResponseEntity.ok("");
    }
}
