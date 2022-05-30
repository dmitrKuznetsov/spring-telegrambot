package com.github.dmitrKuznetsov.stb.bot;

import com.github.dmitrKuznetsov.stb.command.CommandContainer;
import com.github.dmitrKuznetsov.stb.javarushclient.JavaRushGroupClient;
import com.github.dmitrKuznetsov.stb.services.GroupSubService;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageServiceImpl;
import com.github.dmitrKuznetsov.stb.services.StatisticsService;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.dmitrKuznetsov.stb.command.CommandName.NO;
import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getText;

@Component
public class JavaRushTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    JavaRushTelegramBot(TelegramUserService telegramUserService,
                        JavaRushGroupClient javaRushGroupClient,
                        GroupSubService groupSubService,
                        @Value("#{'${bot.admins}'.split(',')}") List<String> admins,
                        StatisticsService statisticsService) {
        commandContainer = new CommandContainer(
                new SendBotMessageServiceImpl(this),
                telegramUserService,
                javaRushGroupClient,
                groupSubService,
                admins,
                statisticsService
        );
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() ) {
            String message = getText(update);
            String userName = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier, userName).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), userName).execute(update);
            }
        }
    }
}
