package com.github.dmitrKuznetsov.stb.repository;

import com.github.dmitrKuznetsov.stb.repository.entity.GroupSub;
import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class TelegramUserRepositoryTest {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/telegramUsers.sql"})
    @Test
    void shouldProperlyFindAllActiveUsers() {
        //when
        List<TelegramUser> telegramUsers = telegramUserRepository.findAllByActiveTrue();

        //then
        assertEquals(5, telegramUsers.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    void shouldProperlySaveTelegramUser() {
        // given
        TelegramUser user = new TelegramUser();
        user.setChatId(121213432L);
        user.setActive(true);
        telegramUserRepository.save(user);

        //when
        Optional<TelegramUser> saved = telegramUserRepository.findById(user.getChatId());

        //then
        assertTrue(saved.isPresent());
        assertEquals(user, saved.get());
    }

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveGroupSubsForUser.sql"})
    @Test
    public void shouldProperlyGetAllGroupSubsForUser() {
        // when
        Optional<TelegramUser> user = telegramUserRepository.findById(1L);

        // then
        assertTrue(user.isPresent());
        List<GroupSub> groupSubs = user.get().getGroupSubs();
        for (int ii = 0; ii < groupSubs.size(); ii++) {
            assertEquals(String.format("g%d", ii + 1), groupSubs.get(ii).getTitle());
            assertEquals(ii + 1, groupSubs.get(ii).getId());
            assertEquals(ii + 1, groupSubs.get(ii).getLastPostId());
        }
    }
}