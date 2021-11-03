package ru.bot.shopper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.shopper.service.BotService;
import ru.bot.shopper.service.ServiceWB;
import ru.bot.shopper.service.enums.LoggerLevel;

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

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (chatId == null) {
                chatId = update.getMessage().getChatId().toString();
            }
            String text = update.getMessage().getText().trim();
            if (Pattern.matches("/update\\s+\\d+", text)) {
                String dtos = serviceWB.getNewOrders(Integer.parseInt(text.split("\\s+")[1]));
                sendUpdate(dtos);
            }
            else {}
        }
    }

    public void sendUpdate(String dto) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(dto);
        try {
            execute(message);
            log(
                    Bot.class,
                    LoggerLevel.INFO,
                    "sendUpdate ",
                    "order #" + dto
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
