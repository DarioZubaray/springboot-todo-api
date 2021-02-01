package com.zubaray.todo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zubaray.todo.models.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    Todo updateTodoMessage(Long id, Todo newTodo);
    Todo updateTodo(Long id, String newMessage);

}
