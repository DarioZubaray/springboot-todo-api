package com.zubaray.todo.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.zubaray.todo.models.Todo;
import com.zubaray.todo.repository.TodoRepository;

@Service
@Profile("dev")
public class InMemoryTodoListRepositoryImpl implements TodoRepository {

    private List<Todo> todoList = new ArrayList<>();

    @Override
    public <S extends Todo> S save(S entity) {
        todoList.add(entity);
        return entity;
    }

    @Override
    public void updateTodoMessage(Long id, Todo newTodo) {
        todoList = todoList.stream()
                .map(todo -> !todo.getId().equals(id) ? todo : newTodo)
                .collect(Collectors.toList());
    }

    @Override
    public void updateTodo(Long id, String newMessage) {
        todoList.stream()
            .filter(todo -> todo.getId().equals(id))
            .peek(todo -> todo.setMessage(newMessage))
            .findFirst()
            .get();
    }

    @Override
    public <S extends Todo> Iterable<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return Optional.of(todoList.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .get());
    }

    @Override
    public boolean existsById(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterable<Todo> findAll() {
        return todoList;
    }

    @Override
    public Iterable<Todo> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long count() {
        return todoList.size();
    }

    @Override
    public void deleteById(Long id) {
        todoList.removeIf(todo -> todo.getId().equals(id));
    }

    @Override
    public void delete(Todo entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll(Iterable<? extends Todo> entities) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        
    }

}
