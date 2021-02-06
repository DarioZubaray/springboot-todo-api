package com.zubaray.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zubaray.todo.models.TodoUser;

@Repository
public interface TodoUserRepository extends CrudRepository<TodoUser, Long> {

    List<TodoUser> findByUsername(String username);
}
