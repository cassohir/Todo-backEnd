package br.com.server.todolist.controllers;

import br.com.server.todolist.models.UserModel;
import br.com.server.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserModel userModel) {
        try {
            return ResponseEntity.ok(userService.createUser(userModel));
        } catch (Exception e){
            System.getLogger("UserController").log(System.Logger.Level.ERROR, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable UUID id){
        System.out.println(id);
        try {
            return ResponseEntity.ok(userService.getUserRepository().getReferenceById(id));
        } catch (Exception e){
            System.getLogger("UserController").log(System.Logger.Level.ERROR, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
