package com.github.dmitrKuznetsov.stb.command;

import org.junit.jupiter.api.DisplayName;


@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return CommandName.HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HelpCommand.HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
