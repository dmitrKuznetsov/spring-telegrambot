package com.github.dmitrKuznetsov.stb.repository;

import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, String> {
    List<TelegramUser> findAllByActiveTrue();
    List<TelegramUser> findAllByActiveFalse();
}




















