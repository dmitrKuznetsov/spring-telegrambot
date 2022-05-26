package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.bot.JavaRushTelegramBot;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageServiceImpl;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommandTest {

    protected JavaRushTelegramBot telegramBot = Mockito.mock(JavaRushTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    abstract String getCommandName();
    abstract String getCommandMessage();
    abstract Command getCommand();

    @Test
    void shouldProperlyExecuteCommand() throws TelegramApiException {
        // given
        Long chatId = 12345678901234L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandMessage());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage(chatId.toString(), getCommandMessage());
        sendMessage.enableHtml(true);

        // when
        getCommand().execute(update);

        // then
        Mockito.verify(telegramBot).execute(sendMessage);
    }
}