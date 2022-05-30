package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.javarushclient.JavaRushGroupClient;
import com.github.dmitrKuznetsov.stb.services.GroupSubService;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import com.github.dmitrKuznetsov.stb.services.StatisticsService;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient javaRushGroupClient = Mockito.mock(JavaRushGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        StatisticsService statisticsService = Mockito.mock(StatisticsService.class);
        commandContainer = new CommandContainer(
                sendBotMessageService,
                telegramUserService,
                javaRushGroupClient,
                groupSubService,
                singletonList("username"),
                statisticsService
        );
    }

    @Test
    void shouldGetAllExistingCommands() {
        // when-then
        Arrays.stream(CommandName.values()).forEach(commandName -> {
            Command command = commandContainer.findCommand(commandName.getCommandName(), "username");
            assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }

    @Test
    void shouldReturnUnknownCommand() {
        // given
        String commandIdentifier = "/some_character_sequence";

        // when
        Command command = commandContainer.findCommand(commandIdentifier, "username");

        // then
        assertEquals(UnknownCommand.class, command.getClass());
    }
}