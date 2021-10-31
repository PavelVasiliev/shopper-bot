package ru.bot.shopper.api.impl.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bot.shopper.api.impl.service.BotService;
import ru.bot.shopper.api.impl.service.ServiceWB;

@RestController
public class TestController {

    private ServiceWB serviceWB;
    private BotService botService;

    @Autowired
    public TestController(ServiceWB serviceWB, BotService botService) {
        this.serviceWB = serviceWB;
        this.botService = botService;
    }

    @GetMapping("/")
    public ResponseEntity testGet() {
        serviceWB.getOrdersByToken("s");
        return ResponseEntity.ok("");
    }

    @Scheduled(cron = "0 0/15 * ? * * *")
    public ResponseEntity getScheduled(String dialogId) {
        serviceWB.getOrdersByDialogId(dialogId);

        //FixMe start
        //botService.
        return ResponseEntity.ok("");
    }
}
