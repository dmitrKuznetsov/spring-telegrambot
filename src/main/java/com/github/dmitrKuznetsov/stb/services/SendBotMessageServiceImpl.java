package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.bot.JavaRushTelegramBot;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavaRushTelegramBot telegramBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaRushTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableHtml(true);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
