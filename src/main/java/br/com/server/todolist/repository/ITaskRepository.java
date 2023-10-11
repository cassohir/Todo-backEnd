package br.com.server.todolist.repository;

import br.com.server.todolist.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {

}
