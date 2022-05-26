package com.github.dmitrKuznetsov.stb.command;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for StatCommandTest")
public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return CommandName.STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(StatCommand.STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
