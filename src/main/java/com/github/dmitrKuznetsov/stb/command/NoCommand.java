package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getChatId;

public class NoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша(/).\n"
            + "Чтобы посмотреть список комманд введи /help";

    NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), NO_MESSAGE);
    }
}
