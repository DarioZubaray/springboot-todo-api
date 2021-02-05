package com.zubaray.todo.utils;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.zubaray.todo.models.Todo;

public class TestConstants {

    public static Todo getValidTodoById_1() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setMessage("Mensaje de prueba");
        todo.setFinished(false);
        todo.setDatetime(LocalDateTime.now());
        return todo;
    }

    public static Iterable<Todo> getValidAllTodo() {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setMessage("Mensaje de prueba I");
        todo1.setFinished(false);
        todo1.setDatetime(LocalDateTime.now());

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setMessage("Mensaje de prueba II");
        todo2.setFinished(true);
        todo2.setDatetime(LocalDateTime.now());

        return Arrays.asList(todo1, todo2);
    }

    public static Todo getValidNewTodo() {
        Todo newTodo = new Todo();
        newTodo.setId(4L);
        newTodo.setMessage("Nuevo todo");
        newTodo.setFinished(false);
        return newTodo;
    }
}
