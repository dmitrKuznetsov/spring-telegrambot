package com.github.dmitrKuznetsov.stb.command;

import com.github.dmitrKuznetsov.stb.repository.entity.GroupSub;
import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import com.github.dmitrKuznetsov.stb.services.SendBotMessageService;
import com.github.dmitrKuznetsov.stb.services.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for ListGroupSubCommandTest")
class ListGroupSubCommandTest {

    @Test
    void shouldProperlyShowsListGroupSub() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1");
        telegramUser.setActive(true);

        List<GroupSub> groupSubList = new ArrayList<>();
        groupSubList.add(populateGroupSub(1,"g1"));
        groupSubList.add(populateGroupSub(2,"g2"));
        groupSubList.add(populateGroupSub(3,"g3"));
        groupSubList.add(populateGroupSub(4,"g4"));

        telegramUser.setGroupSubs(groupSubList);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId()))
                .thenReturn(Optional.of(telegramUser));

        ListGroupSubCommand command = new ListGroupSubCommand(sendBotMessageService, telegramUserService);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(telegramUser.getChatId()));
        Mockito.when(message.getText()).thenReturn(CommandName.LIST_GROUP_SUB.getCommandName());
        update.setMessage(message);

        String collectedGroups = telegramUser.getGroupSubs().stream()
                .map(group -> String.format("Группа: %s, ID = %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());
        String text = String.format("Я нашел все подписки на группы: \n\n %s", collectedGroups);

        // when
        command.execute(update);

        // then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), text);
    }

    private GroupSub populateGroupSub(Integer id, String title) {
        GroupSub gs = new GroupSub();
        gs.setId(id);
        gs.setTitle(title);
        return gs;
    }
}