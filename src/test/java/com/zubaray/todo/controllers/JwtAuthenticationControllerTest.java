package com.zubaray.todo.controllers;

import static com.zubaray.todo.utils.TestConstants.JSON_WEB_TOKEN;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zubaray.todo.config.JwtTokenUtil;
import com.zubaray.todo.models.JwtRequest;
import com.zubaray.todo.repository.TodoRepository;
import com.zubaray.todo.services.JwtUserDetailsService;
import com.zubaray.todo.utils.TestConstants;

@WebMvcTest(JwtAuthenticationController.class)
@ComponentScan("com.zubaray.todo")
class JwtAuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoRepository repository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService jwtUserDetailService;

    @Test
    void testCreateAuthenticationToken_ok() throws Exception {
        when(authenticationManager.authenticate(Mockito.any()))
            .thenReturn(null);
        when(jwtTokenUtil.generateToken(Mockito.any()))
            .thenReturn(TestConstants.JSON_WEB_TOKEN);
        when(jwtUserDetailService.loadUserByUsername(Mockito.anyString()))
            .thenReturn(new User("admin", "root", new ArrayList<>()));

        JwtRequest request = new JwtRequest("admin", "root");

        mvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwttoken", is(JSON_WEB_TOKEN)));
    }
}
