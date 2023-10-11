package br.com.server.todolist.service;

import br.com.server.todolist.models.TaskModel;
import br.com.server.todolist.repository.ITaskRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class TaskService {
    @Autowired
    private ITaskRepository taskRepository;

    public TaskModel createTask(TaskModel task) throws Exception {
        try {
            return this.taskRepository.save(task);
        }catch (Exception e){
            throw new Exception("Error to create Task");
        }
    }

}
