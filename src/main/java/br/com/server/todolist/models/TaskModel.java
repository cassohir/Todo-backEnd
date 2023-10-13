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
    private UUID idUser;
    @Column(length = 50)
    private String title;
    private String priority;
    private String description;
    private LocalDateTime endAt;
    private LocalDateTime startAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception{
            if (title.length() > 50) {
                throw new Exception("The Title field must have a maximum of 50 characters ");
            }
            this.title = title;
    }

}
