package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.dmitrKuznetsov.stb.command.CommandName.STAT;
import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getChatId;
import static java.lang.String.format;

public class AdminHelpCommand implements Command {

    public static final String ADMIN_HELP_MESSAGE = format("✨<b>Доступные команды админа</b>✨\n\n"
                    + "<b>Получить статистику</b>\n"
                    + "%s - статистика бота\n",
            STAT.getCommandName());

    private final SendBotMessageService sendBotMessageService;

    public AdminHelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), ADMIN_HELP_MESSAGE);
    }
}
