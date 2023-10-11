package br.com.server.todolist.controllers;

import br.com.server.todolist.models.TaskModel;
import br.com.server.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TaskModel task) {
        try {
            return ResponseEntity.ok(taskService.createTask(task));
        } catch (Exception e){
            System.getLogger("TaskController").log(System.Logger.Level.ERROR, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
