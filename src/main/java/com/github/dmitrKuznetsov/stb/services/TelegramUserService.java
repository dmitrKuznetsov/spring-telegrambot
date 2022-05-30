package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {

    void save(TelegramUser telegramUser);

    Optional<TelegramUser> findByChatId(Long chatId);

    List<TelegramUser> findAllActiveUsers();

    List<TelegramUser> findAllInActiveUsers();
}
