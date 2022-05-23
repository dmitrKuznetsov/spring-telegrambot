package com.github.dmitrKuznetsov.stb.repository;

import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

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
        Assertions.assertEquals(5, telegramUsers.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    void shouldProperlySaveTelegramUser() {
        // given
        TelegramUser user = new TelegramUser();
        user.setChatId("121213432");
        user.setActive(true);
        telegramUserRepository.save(user);

        //when
        Optional<TelegramUser> saved = telegramUserRepository.findById(user.getChatId());

        //then
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(user, saved.get());
    }
}