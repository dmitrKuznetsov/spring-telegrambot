package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getChatId;

public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static String STOP_MESSAGE = "Деактивировал все твои подписки \uD83D\uDE1F.\n" +
            "Ты всегда можешь вернуться нажав /start";

    StopCommand(SendBotMessageService sendBotMessageService,
                TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = getChatId(update);

        telegramUserService.findByChatId(chatId).ifPresent(telegramUser -> {
            telegramUser.setActive(false);
            telegramUserService.save(telegramUser);
        });

        sendBotMessageService.sendMessage(chatId, STOP_MESSAGE);
    }
}
