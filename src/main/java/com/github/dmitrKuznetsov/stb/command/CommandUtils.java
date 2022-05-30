package com.github.dmitrKuznetsov.stb.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandUtils {

    public static Long getChatId(Update update) {
        return update.getMessage().getChatId();
    }

    public static String getText(Update update) {
        return update.getMessage().getText().trim();
    }
}
