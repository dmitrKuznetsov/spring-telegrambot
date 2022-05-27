package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getChatId;

public class ListGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService,
                               TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        // todo add exception handling
        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update))
                .orElseThrow(NumberFormatException::new);

        String message;
        if(CollectionUtils.isEmpty(telegramUser.getGroupSubs())) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши /addgroupsub";
        } else {
            String collectedGroups = telegramUser.getGroupSubs().stream()
                    .map(group -> String.format("Группа: %s, ID = %s \n", group.getTitle(), group.getId()))
                    .collect(Collectors.joining());
            message =  String.format("Я нашел все подписки на группы: \n\n%s", collectedGroups);
        }

        sendBotMessageService.sendMessage(telegramUser.getChatId(), message);
    }
}
