package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.javarushclient.JavaRushGroupClient;
import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupDiscussionInfo;
import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupRequestArgs;
import com.github.dmitrKuznetsov.stb.repository.entity.GroupSub;
import com.github.dmitrKuznetsov.stb.services.GroupSubService;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getChatId;
import static com.github.dmitrKuznetsov.stb.command.CommandUtils.getText;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

//todo add unit test for the command logic.
public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    AddGroupSubCommand(SendBotMessageService sendBotMessageService,
                       JavaRushGroupClient javaRushGroupClient,
                       GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (getText(update).equalsIgnoreCase(CommandName.ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getText(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.valueOf(groupId));
            if (isNull(groupById)) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupNotFound(Long chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupIdList(Long chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай комадну вместе с ID группы. \n" +
                "Например: /addgroupsub 16. \n\n" +
                "я подготовил список всех групп - выберай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}
