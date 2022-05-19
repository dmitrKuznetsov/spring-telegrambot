package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static String START_MESSAGE = "Привет. Я Javarush Telegram Bot. Я помогу тебе быть в курсе последних " +
            "статей тех авторов, котрые тебе интересны. Я еще маленький и только учусь.";

    StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
