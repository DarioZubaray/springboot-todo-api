package com.zubaray.todo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/findby/{todoId}")
    public ResponseEntity<?> findById(@PathVariable String todoId) {
        log.debug("Finding Todo by id: {}", todoId);
        try {
            Long id = Long.valueOf(todoId);
            Optional<Todo> todo = repository.findById(id);
            if (todo.isPresent()) {
                return ResponseEntity.ok(todo.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public List<Todo> findAll() {
        log.debug("Findind all Todos");
        List<Todo> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @PostMapping()
    public ResponseEntity<Todo> addTodo(@Valid @RequestBody Todo todo) {
        log.debug("Saving new Todo: {}", todo.getMessage());
        Todo todoSaved = repository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoSaved);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> updateTodoMessage(@PathVariable Long id, @Valid @RequestBody Todo newTodo) {
        log.debug("Put - Updating Todo with id: {}", id);
        repository.updateTodoMessage(id, newTodo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{id}/{newMessage}")
    @Transactional
    public ResponseEntity<Void> updateTodo(@PathVariable Long id, @PathVariable String newMessage) {
        log.debug("Patch - Updating Todo with id: {}", id);
        repository.updateTodo(id, newMessage);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        log.debug("Deleting Todo with id: {}", id);
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
