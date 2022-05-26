package com.github.dmitrKuznetsov.stb.javarushclient;

import com.github.dmitrKuznetsov.stb.javarushclient.dto.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaRushGroupClientTest {

    private final JavaRushGroupClient client = new JavaRushGroupClientImpl("https://javarush.ru/api/1.0/rest");

    @Test
    public void shouldProperlyGetGroupsWithEmptyArgs() {
        // given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder().build();

        // when
        List<GroupInfo> groupList = client.getGroupList(requestArgs);

        // then
        assertNotNull(groupList);
        assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetWithOffSetAndLimit() {
        // given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        // when
        List<GroupInfo> groupList = client.getGroupList(requestArgs);

        // then
        assertNotNull(groupList);
        assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupsDiscWithEmptyArgs() {
// given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder().build();

        // when
        List<GroupDiscussionInfo> groupList = client.getGroupDiscussionList(requestArgs);

        // then
        assertNotNull(groupList);
        assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupDiscWithOffSetAndLimit() {
        // given
        GroupRequestArgs requestArgs = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        // when
        List<GroupDiscussionInfo> groupList = client.getGroupDiscussionList(requestArgs);

        // then
        assertNotNull(groupList);
        assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupCount() {
        // given
        GroupsCountRequestArgs requestArgs = GroupsCountRequestArgs.builder().build();

        // when
        Integer groupCount = client.getGroupCount(requestArgs);

        // then
        assertEquals(32, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupTECHCount() {
        // given
        GroupsCountRequestArgs requestArgs = GroupsCountRequestArgs.builder()
                .type(GroupInfoType.TECH)
                .build();

        // when
        Integer groupCount = client.getGroupCount(requestArgs);

        // then
        assertEquals(7, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupById() {
        // given
        Integer androidGroupId = 16;

        // when
        GroupDiscussionInfo groupById = client.getGroupById(androidGroupId);

        // then
        assertNotNull(groupById);
        assertEquals(androidGroupId, groupById.getId());
        assertEquals(GroupInfoType.TECH, groupById.getType());
        assertEquals("android", groupById.getKey());
    }
}