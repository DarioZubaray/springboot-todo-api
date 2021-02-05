package com.zubaray.todo.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Message is mandatory")
    private String message;
    @NotNull(message = "Finished is mandatory")
    private Boolean finished;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Datetime is mandatory")
    private LocalDateTime datetime;

}
