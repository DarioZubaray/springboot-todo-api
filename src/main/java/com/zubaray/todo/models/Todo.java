package com.zubaray.todo.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Todo {

    @Id
    private Long id;
    private String message;
    private Boolean finished;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime datetime;

}
