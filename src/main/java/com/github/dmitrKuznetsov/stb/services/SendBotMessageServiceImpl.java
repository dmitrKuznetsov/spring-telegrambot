package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.bot.JavaRushTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavaRushTelegramBot telegramBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaRushTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), message);
        sendMessage.enableHtml(true);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Long chatId, List<String> messages) {
        if (isEmpty(messages)) return;

        messages.forEach(m -> sendMessage(chatId, m));
    }
}
