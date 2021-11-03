package ru.bot.shopper.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bot.shopper.Bot;
import ru.bot.shopper.service.ServiceWB;

@EnableScheduling
@RestController
public class TestController {

    private ServiceWB serviceWB;
    private Bot bot;


    public TestController(ServiceWB serviceWB, Bot bot) {
        this.serviceWB = serviceWB;
        this.bot = bot;
    }

    @GetMapping("/")
    @Scheduled(cron = "1 */15 * * * *") //every 15 mins
    public ResponseEntity sendUpdatesScheduled() {
        String orders = serviceWB.getNewOrders(null);
        bot.sendUpdate(orders);
        return ResponseEntity.ok("");
    }
}
