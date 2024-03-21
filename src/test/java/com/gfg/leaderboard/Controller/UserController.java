package com.gfg.leaderboard.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.leaderboard.entity.User;
import com.gfg.leaderboard.service.UserService;

@SpringBootTest
public class UserController {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreateUser_Success() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@example.com");

        String expectedJson= mapToJson(user);

        String url= "/api/user";
        
        RequestBuilder builder = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(expectedJson);

        MvcResult result = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputJson = response.getContentAsString();
        assertEquals(expectedJson, outputJson);



       
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }





    
}
