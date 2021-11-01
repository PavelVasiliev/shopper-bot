package ru.bot.shopper.api.impl.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.shopper.api.impl.service.BotService;
import ru.bot.shopper.api.impl.service.ServiceWB;
import ru.bot.shopper.api.impl.service.enums.LoggerLevel;
import ru.bot.shopper.api.model.OrderDto;

import java.util.List;
import java.util.regex.Pattern;

@Component
@Configuration
public class Bot extends TelegramLongPollingBot implements BotService {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;
    private String chatId = "-769749131"; //list of id's

    @Autowired
    private ServiceWB serviceWB;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getChatId().toString());
        String text;
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (chatId == null) {
                chatId = update.getMessage().getChatId().toString();
            }
            text = update.getMessage().getText().trim();
            if (Pattern.matches("/update\\s+\\d+", text)) {
                List<OrderDto> dtos = serviceWB.getNewOrders(Integer.parseInt(text.split("\\s+")[1]));
                dtos.forEach(this::sendUpdate);
            }
        }
    }

    public void sendUpdate(OrderDto dto) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(dto.toPrint());
        try {
            execute(message);
            log(
                    Bot.class,
                    LoggerLevel.INFO,
                    "sendUpdate ",
                    "order #" + dto.getNumber()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
