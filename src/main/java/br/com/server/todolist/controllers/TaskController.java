package br.com.server.todolist.controllers;

import br.com.server.todolist.models.TaskModel;
import br.com.server.todolist.service.TaskService;
import br.com.server.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TaskModel task, HttpServletRequest request) {
        try {
            task.setIdUser((UUID) request.getAttribute("idUser"));
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
        } catch (Exception e){
            System.getLogger("TaskController").log(System.Logger.Level.ERROR, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest request) {
        try {
            UUID idUser = (UUID) request.getAttribute("idUser");
            return ResponseEntity.ok(taskService.getMyTasks(idUser));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<?> update(@RequestBody TaskModel task, HttpServletRequest request, @PathVariable UUID taskId){
        try {
            Optional<TaskModel> optional = taskService.getTask(taskId);
            if (optional.isPresent()){
                TaskModel taskToUpdate = optional.get();
                if(!taskToUpdate.getIdUser().equals(request.getAttribute("idUser"))) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User has no permission to update this task!");
                }
                Utils.copyNonNullProperties(task, taskToUpdate);
                return ResponseEntity.ok(taskService.createTask(task));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The task was not found");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{taskId}")
    public ResponseEntity<?> findOne(@PathVariable UUID taskId){
        try {
            Optional<TaskModel> task = taskService.getTask(taskId);
            if(task.isPresent()) return ResponseEntity.ok(task.get());
            else return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
