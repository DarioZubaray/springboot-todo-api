package com.zubaray.todo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zubaray.todo.models.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Todo t SET t.message =:#{#newTodo.message}, t.finished =:#{#newTodo.finished} where t.id =:id")
    public void updateTodoMessage(@Param("id") Long id, @Param("newTodo") Todo newTodo);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Todo t SET t.message =:newMessage where t.id =:id")
    public void updateTodo(@Param("id") Long id, @Param("newMessage") String newMessage);

}
