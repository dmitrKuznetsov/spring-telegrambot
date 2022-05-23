package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static String STOP_MESSAGE = "Деактивировал все ваши подписки \uD83D\uDE1F.";

    StopCommand(SendBotMessageService sendBotMessageService,
                TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatId).ifPresent(telegramUser -> {
            telegramUser.setActive(false);
            telegramUserService.save(telegramUser);
        });

        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
    }
}
