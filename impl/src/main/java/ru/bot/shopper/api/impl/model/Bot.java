package ru.bot.shopper.api.impl.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.shopper.api.model.OrderDto;

@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    private String chatId = "769749131"; //list of id's

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
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if(chatId == null){
                chatId = update.getMessage().getChatId().toString();
            }
            SendMessage sm = new SendMessage();
            sm.setChatId(chatId);
            sm.setText(message);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendUpdate(OrderDto dto){
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(dto.toString());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
