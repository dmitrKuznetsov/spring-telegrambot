package com.github.dmitrKuznetsov.stb.javarushclient;

import com.github.dmitrKuznetsov.stb.javarushclient.dto.PostInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.dmitrKuznetsov.stb.javarushclient.JavaRushGroupClientTest.JAVARUSH_API_PATH;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration-level testing for JavaRushPostClient")
class JavaRushPostClientTest {

    private final JavaRushPostClient postClient = new JavaRushPostClientImpl(JAVARUSH_API_PATH);

    @Test
    public void shouldProperlyGetNew15Posts() {
        //when
        List<PostInfo> newPosts = postClient.findNewPosts(30, 2935);

        //then
        assertEquals(15, newPosts.size());
    }
}