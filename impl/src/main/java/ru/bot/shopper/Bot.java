package ru.bot.shopper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.shopper.service.BotService;
import ru.bot.shopper.service.ServiceWB;
import ru.bot.shopper.service.enums.LoggerLevel;
import ru.bot.shopper.util.Utils;

import java.util.regex.Pattern;

import static ru.bot.shopper.util.Utils.*;

@Component
@Configuration
public class Bot extends TelegramLongPollingBot implements BotService {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    private String chatId = "-769749131"; //list of id's

    private final ServiceWB serviceWB;

    public Bot(ServiceWB serviceWB) {
        this.serviceWB = serviceWB;
    }

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
        log(Bot.class, LoggerLevel.INFO, "onUpdateReceived()" ,update.getMessage().getChatId().toString());

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (chatId == null)  chatId = getChartId(update);

            String text = getText(update);
            if (Pattern.matches("/update\\s+\\d+", text)) {
                String answer = serviceWB.getNewOrders(getCountMinutes(text));
                if(!answer.isEmpty()) sendUpdate(answer);
                else sendUpdate("Ничего не наторговал, работай усерднее!");
            }
        }
    }


    @Scheduled(cron = "1 */15 * * * *")
    private void scheduleUpdate(){
        String answer = serviceWB.getNewOrders();
        if(!answer.isEmpty()) sendUpdate(answer);

    }

    private void sendUpdate(String dto) {
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
