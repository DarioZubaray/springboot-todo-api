package com.zubaray.todo.controllers;

import static com.zubaray.todo.utils.TestConstants.JSON_WEB_TOKEN;
import static com.zubaray.todo.utils.TestConstants.getValidNewTodo;
import static com.zubaray.todo.utils.TestConstants.getValidTodoById_1;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zubaray.todo.config.JwtTokenUtil;
import com.zubaray.todo.models.Todo;
import com.zubaray.todo.repository.TodoRepository;
import com.zubaray.todo.services.JwtUserDetailsService;
import com.zubaray.todo.utils.TestConstants;

@WebMvcTest(TodoController.class)
@ComponentScan("com.zubaray.todo")
class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoRepository repository;

    @MockBean
    private JwtUserDetailsService jwtUserDetailService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setup() {
        when(jwtTokenUtil.getUsernameFromToken(Mockito.anyString()))
            .thenReturn("admin");
        when(jwtUserDetailService.loadUserByUsername(Mockito.anyString()))
            .thenReturn(new User("admin", "root", new ArrayList<>()));
        when(jwtTokenUtil.validateToken(Mockito.anyString(), Mockito.any()))
            .thenReturn(true);
    }

    @Test
    void testFindTodoById_Ok() throws Exception {
        when(repository.findById(Mockito.anyLong()))
            .thenReturn(Optional.of(getValidTodoById_1()));


        mvc.perform(get("/api/todo/findby/1")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(getValidTodoById_1().getId().intValue())))
                .andExpect(jsonPath("$.message", is(getValidTodoById_1().getMessage())))
                .andExpect(jsonPath("$.finished", is(getValidTodoById_1().getFinished())))
                .andExpect(jsonPath("$.datetime", is(anything())));
    }

    @Test
    void testFindTodoById_NotFound() throws Exception {
        when(repository.findById(Mockito.anyLong()))
            .thenReturn(Optional.empty());

        mvc.perform(get("/api/todo/findby/4")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindTodoById_BadRequest() throws Exception {
        when(repository.findById(Mockito.anyLong()))
           .thenReturn(Optional.empty());

        mvc.perform(get("/api/todo/findby/aaa")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindAllTodo() throws Exception {
        when(repository.findAll())
            .thenReturn(TestConstants.getValidAllTodo());

        mvc.perform(get("/api/todo/all")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].message", containsInAnyOrder("Mensaje de prueba I", "Mensaje de prueba II")))
                .andExpect(jsonPath("$[*].finished", containsInAnyOrder(false, true)))
                .andExpect(jsonPath("$[*].datetime", is(anything())));
    }

    @Test
    void testaddTodo_ok() throws Exception {
        when(repository.save(Mockito.any()))
            .thenReturn(TestConstants.getValidNewTodo());
        Todo todo = getValidNewTodo();

        mvc.perform(post("/api/todo/")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(todo)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(todo.getId().intValue())))
                .andExpect(jsonPath("$.message", is(todo.getMessage())))
                .andExpect(jsonPath("$.finished", is(todo.getFinished())))
                .andExpect(jsonPath("$.datetime", is(anything())));
    }

    @Test
    void testaddTodo_badRequest() throws Exception {
        mvc.perform(post("/api/todo/")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Todo())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(anything())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors[*]", containsInAnyOrder("Datetime is mandatory", "Message is mandatory", "Finished is mandatory")));
    }

    @Test
    void testUpdateTodoMessage_Ok() throws Exception {
        doNothing().when(repository).updateTodoMessage(Mockito.anyLong(), Mockito.any());
        Todo todo = getValidNewTodo();

        mvc.perform(put("/api/todo/1")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(todo)))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void testUpdateTodoMessage_badRequest() throws Exception {
        mvc.perform(put("/api/todo/1")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Todo())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(anything())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors[*]", containsInAnyOrder("Datetime is mandatory", "Message is mandatory", "Finished is mandatory")));
;
    }

    @Test
    void testUpdateTodo_Ok() throws Exception {
        doNothing().when(repository).updateTodo(Mockito.anyLong(), Mockito.any());

        mvc.perform(patch("/api/todo/1/Otro Mensaje")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void testDeleteTodo_Ok() throws Exception {
        doNothing().when(repository).deleteById(Mockito.anyLong());

        mvc.perform(delete("/api/todo/1")
                .header("Authorization", "Bearer ".concat(JSON_WEB_TOKEN))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
