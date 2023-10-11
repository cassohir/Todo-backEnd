package br.com.server.todolist.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name="tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID idUSer;
    @Column(length = 50)
    private String title;
    private String priority;
    private String description;
    private LocalDateTime endAt;
    private LocalDateTime startAt;

    @CreationTimestamp
    private LocalDateTime createdAt;



}
