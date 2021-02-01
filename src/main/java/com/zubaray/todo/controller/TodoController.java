package com.zubaray.todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zubaray.todo.models.Todo;
import com.zubaray.todo.repository.TodoRepository;

@RestController()
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    @Qualifier("InMemoryTodoListRepositoryImpl")
    private TodoRepository repository;

    private List<Todo> todoList = new ArrayList<>();

    @GetMapping("/findby/{id}")
    public Todo findById(@PathVariable Long id) {
        return todoList.stream()
                 .filter(todo -> todo.getId().equals(id))
                 .findFirst()
                 .get();
    }

    @GetMapping("/all")
    public List<Todo> findAll() {
        return todoList;
    }

    @PostMapping()
    public Todo addEntity(@RequestBody Todo todo) {
        todoList.add(todo);
        return todo;
    }

    @PutMapping("/{id}")
    public Todo updateTodoMessage(@PathVariable Long id, @RequestBody Todo newTodo) {
        todoList = todoList.stream()
                .map(todo -> !todo.getId().equals(id) ? todo : newTodo)
                .collect(Collectors.toList());
        return newTodo;
    }

    @PatchMapping("/{id}/{newMessage}")
    public Todo updateTodoMessage(@PathVariable Long id, @PathVariable String newMessage) {
        return todoList.stream()
                    .filter(todo -> todo.getId().equals(id))
                    .peek(todo -> todo.setMessage(newMessage))
                    .findFirst()
                    .get();
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoList.removeIf(todo -> todo.getId().equals(id));
    }

}
