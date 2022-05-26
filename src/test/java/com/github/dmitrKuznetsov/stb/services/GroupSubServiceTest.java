package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupDiscussionInfo;
import com.github.dmitrKuznetsov.stb.repository.GroupSubRepository;
import com.github.dmitrKuznetsov.stb.repository.entity.GroupSub;
import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for GroupSubService")
class GroupSubServiceTest {

    private GroupSubService groupSubService;
    private GroupSubRepository groupSubRepository;
    private TelegramUser telegramUser;

    private final static String CHAT_ID = "1";

    @BeforeEach
    void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        groupSubRepository = Mockito.mock(GroupSubRepository.class);
        groupSubService = new GroupSubServiceImpl(groupSubRepository,telegramUserService);

        telegramUser = new TelegramUser();
        telegramUser.setChatId(CHAT_ID);
        telegramUser.setActive(true);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(telegramUser));
    }

    @Test
    public void shouldProperlySaveGroup() {
        // given
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(telegramUser);

        // when
        groupSubService.save(CHAT_ID, groupDiscussionInfo);

        // then
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }

    @Test
    public void shouldProperlyAddUserToExistingGroup() {
        // given
        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId("2");
        oldTelegramUser.setActive(true);

        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        GroupSub groupFromDB = new GroupSub();
        groupFromDB.setId(groupDiscussionInfo.getId());
        groupFromDB.setTitle(groupDiscussionInfo.getTitle());
        groupFromDB.addUser(oldTelegramUser);

        Mockito.when(groupSubRepository.findById(groupDiscussionInfo.getId()))
                .thenReturn(Optional.of(groupFromDB));

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(oldTelegramUser);
        expectedGroupSub.addUser(telegramUser);

        // when
        groupSubService.save(CHAT_ID, groupDiscussionInfo);

        // then
        Mockito.verify(groupSubRepository).findById(groupDiscussionInfo.getId());
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }
}