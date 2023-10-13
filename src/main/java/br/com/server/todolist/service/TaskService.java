package br.com.server.todolist.service;

import br.com.server.todolist.models.TaskModel;
import br.com.server.todolist.repository.ITaskRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
public class TaskService {
    @Autowired
    private ITaskRepository taskRepository;

    public TaskModel createTask(TaskModel task) throws Exception {
        try {
            LocalDateTime currentDate = LocalDateTime.now();
            if(currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt()))
                throw new Exception("The start date and end date must be greater than the current date");
            if(task.getStartAt().isAfter(task.getEndAt()))
                throw new Exception("The start date must be less than end date ");
            return this.taskRepository.save(task);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<TaskModel> getMyTasks(UUID idUser) {
        return taskRepository.findByIdUser(idUser);
    }
    public Optional<TaskModel> getTask(UUID taskId){
        return this.taskRepository.findById(taskId);
    }

}
