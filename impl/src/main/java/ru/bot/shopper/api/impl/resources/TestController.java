package ru.bot.shopper.api.impl.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bot.shopper.api.impl.service.ServiceWB;

@RestController
public class TestController {

    private ServiceWB serviceWB;

    public TestController(ServiceWB serviceWB) {
        this.serviceWB = serviceWB;
    }

    @GetMapping("/")
    public ResponseEntity testGet(){
        serviceWB.getOrdersByToken("s");
        return ResponseEntity.ok("");
    }
}
