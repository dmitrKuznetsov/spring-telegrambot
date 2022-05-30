package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.dto.StatisticDTO;

/**
 * Service for getting bot statistics.
 */
public interface StatisticsService {
    StatisticDTO countBotStatistic();
}