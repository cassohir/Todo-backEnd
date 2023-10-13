package br.com.server.todolist.utils;


import lombok.Getter;

@Getter
public enum Paths {
    TASKS("/task"),
    USER("/user");

    private final String path;

    Paths(String path) {
        this.path = path;
    }
}
