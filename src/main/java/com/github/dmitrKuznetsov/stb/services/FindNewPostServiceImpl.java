package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.javarushclient.JavaRushPostClient;
import com.github.dmitrKuznetsov.stb.javarushclient.dto.PostInfo;
import com.github.dmitrKuznetsov.stb.repository.entity.GroupSub;
import com.github.dmitrKuznetsov.stb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewPostServiceImpl implements FindNewPostService {

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.ru/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendMessageService;

    @Autowired
    public FindNewPostServiceImpl(GroupSubService groupSubService,
                                  JavaRushPostClient javaRushPostClient,
                                  SendBotMessageService sendMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendMessageService = sendMessageService;
    }


    @Override
    public void findNewPosts() {
        groupSubService.findAll().forEach(gSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(gSub.getId(), gSub.getLastPostId());

            setNewLastPostId(gSub, newPosts);

            notifySubscribersAboutNewPosts(gSub, newPosts);
        });
    }

    private void setNewLastPostId(GroupSub gSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    gSub.setLastPostId(id);
                    groupSubService.save(gSub);
                });
    }

    private void notifySubscribersAboutNewPosts(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewPosts = newPosts.stream()
                .map(post -> String.format("✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨\n\n" +
                                "<b>Описание:</b> %s\n\n" +
                                "<b>Ссылка:</b> %s\n",
                        post.getTitle(), gSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());

        gSub.getUsers().stream()
                .filter(TelegramUser::getActive)
                .forEach(it -> sendMessageService.sendMessage(it.getChatId(), messagesWithNewPosts));
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }
}
