package com.zubaray.todo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @GetMapping("/findby/{id}")
    public Todo findById(@PathVariable Long id) {
        log.debug("Finding Todo by id: {}", id);
        return repository.findById(id).get();
    }

    @GetMapping("/all")
    public List<Todo> findAll() {
        log.debug("Findind all Todos");
        List<Todo> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @PostMapping()
    public Todo addEntity(@RequestBody Todo todo) {
        log.debug("Saving new Todo: {}", todo.getMessage());
        return repository.save(todo);
    }

    @PutMapping("/{id}")
    @Transactional
    public void updateTodoMessage(@PathVariable Long id, @RequestBody Todo newTodo) {
        log.debug("Put - Updating Todo with id: {}", id);
        repository.updateTodoMessage(id, newTodo);
    }

    @PatchMapping("/{id}/{newMessage}")
    @Transactional
    public void updateTodo(@PathVariable Long id, @PathVariable String newMessage) {
        log.debug("Patch - Updating Todo with id: {}", id);
        repository.updateTodo(id, newMessage);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteTodo(@PathVariable Long id) {
        log.debug("Deleting Todo with id: {}", id);
        repository.deleteById(id);
    }

}
