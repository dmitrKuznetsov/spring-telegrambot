package com.github.dmitrKuznetsov.stb.job;

import com.github.dmitrKuznetsov.stb.services.FindNewPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
public class FindNewPostsJob {

    private final FindNewPostService findNewPostService;

    @Autowired
    public FindNewPostsJob(FindNewPostService findNewPostService) {
        this.findNewPostService = findNewPostService;
    }

    @Scheduled(fixedRateString = "${bot.recountNewPostFixedRate}")
    public void findNewPost() {
        LocalDateTime start = LocalDateTime.now();

        log.info("Find new post job started");

        findNewPostService.findNewPosts();

        LocalDateTime end = LocalDateTime.now();

        log.info("Find new posts job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
