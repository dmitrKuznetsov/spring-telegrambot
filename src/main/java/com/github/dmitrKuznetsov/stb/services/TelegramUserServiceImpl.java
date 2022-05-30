package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.repository.TelegramUserRepository;
import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository repository;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository repository) {
        this.repository = repository;
    }


    @Override
    public void save(TelegramUser telegramUser) {
        repository.save(telegramUser);
    }

    @Override
    public Optional<TelegramUser> findByChatId(Long chatId) {
        return repository.findById(chatId);
    }

    @Override
    public List<TelegramUser> findAllActiveUsers() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public List<TelegramUser> findAllInActiveUsers() {
        return repository.findAllByActiveFalse();
    }
}
