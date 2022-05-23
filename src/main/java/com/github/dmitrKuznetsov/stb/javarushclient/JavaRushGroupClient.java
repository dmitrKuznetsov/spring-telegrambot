package com.github.dmitrKuznetsov.stb.javarushclient;

import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupDiscussionInfo;
import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupInfo;
import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupRequestArgs;
import com.github.dmitrKuznetsov.stb.javarushclient.dto.GroupsCountRequestArgs;

import java.util.List;

public interface JavaRushGroupClient {

    List<GroupInfo> getGroupList(GroupRequestArgs requestArgs);

    List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs);

    Integer getGroupCount(GroupsCountRequestArgs countRequestArgs);

    GroupDiscussionInfo getGroupById(Integer id);
}
