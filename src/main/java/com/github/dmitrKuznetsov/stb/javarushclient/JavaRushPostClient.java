package com.github.dmitrKuznetsov.stb.javarushclient;

import com.github.dmitrKuznetsov.stb.javarushclient.dto.PostInfo;

import java.util.List;

public interface JavaRushPostClient {

    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);
}