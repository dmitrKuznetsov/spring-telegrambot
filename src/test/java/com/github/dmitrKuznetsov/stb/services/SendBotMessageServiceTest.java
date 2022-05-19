package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.bot.JavaRushTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
class SendBotMessageServiceTest {

    JavaRushTelegramBot telegramBot;
    SendBotMessageService sendBotMessageService;

    @BeforeEach
    void init() {
        telegramBot = Mockito.mock(JavaRushTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);
    }

    @Test
    void shouldProperlySendMessageTest() throws TelegramApiException {
        // given
        String chatId = "string_chat_id";
        String message = "string_message";

        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.enableHtml(true);

        // when
        sendBotMessageService.sendMessage(chatId, message);

        // then
        Mockito.verify(telegramBot).execute(sendMessage);
    }
}