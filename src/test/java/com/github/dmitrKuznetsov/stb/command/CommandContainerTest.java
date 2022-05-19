package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        commandContainer = new CommandContainer(sendBotMessageService);
    }

    @Test
    void shouldGetAllExistingCommands() {
        // when-then
        Arrays.stream(CommandName.values()).forEach(commandName -> {
            Command command = commandContainer.retrieveCommand(commandName.getCommandName());
            Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }

    @Test
    void shouldReturnUnknownCommand() {
        // given
        String commandIdentifier = "/some_character_sequence";

        // when
        Command command = commandContainer.retrieveCommand(commandIdentifier);

        // then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}