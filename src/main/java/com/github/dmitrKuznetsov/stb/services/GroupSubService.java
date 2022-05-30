package com.github.dmitrKuznetsov.stb.services;

import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupDiscussionInfo;
import com.github.dmitrKuznetsov.stb.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

public interface GroupSubService {

    GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo);
    GroupSub save(GroupSub groupSub);
    Optional<GroupSub> findById(Integer id);
    List<GroupSub> findAll();
}
