package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getChatId;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static String START_MESSAGE = "Привет. Я Javarush Telegram Bot.\n " +
            "Я помогу тебе быть в курсе последних статей тех авторов, которые тебе интересны.\n\n" +
            "Нажимай /addgroupsub чтобы подписаться на группу статей в JavaRush.\n" +
            "Не знаешь о чем я? Напиши /help, чтобы узнать что я умею.";

    StartCommand(SendBotMessageService sendBotMessageService,
                 TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = getChatId(update);

        Optional<TelegramUser> optionalTelegramUser = telegramUserService.findByChatId(chatId);
        TelegramUser telegramUser;
        if (optionalTelegramUser.isPresent()) {
            telegramUser = optionalTelegramUser.get();
        } else {
            telegramUser = new TelegramUser();
            telegramUser.setChatId(chatId);
        }
        telegramUser.setActive(true);
        telegramUserService.save(telegramUser);

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
