package com.waterfy.projeto.tasks;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.tasks.dto.CreateTaskDTO;
import com.waterfy.projeto.tasks.dto.TaskDTO;
import com.waterfy.projeto.tasks.dto.UpdateTaskDTO;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/tasks")
@RestController
public class TasksController {
    private final TasksServices tasksServices;

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) final TaskStatus status,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size) {
        return tasksServices.getTasksWithParams(status, page, size);
    }

    @GetMapping(path = "/{id}")
    public Task getTaskById(@PathVariable("id") final Long id) {
        return tasksServices.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody final CreateTaskDTO createTaskDTO) {
        final Task task = tasksServices.saveTask(createTaskDTO.toTask());
        return new ResponseEntity<>(TaskDTO.fromTask(task), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> putUpdateTask(@PathVariable final Long id,
            @Valid @RequestBody final UpdateTaskDTO updateTaskDTO) {
        final Task task = tasksServices.updateTask(id, updateTaskDTO.toTask());
        return new ResponseEntity<>(TaskDTO.fromTask(task), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable final Long id) {
        tasksServices.deleteTask(id);
    }

    @DeleteMapping
    public void deleteAllTasks() {
        tasksServices.deleteAllTasks();
    }

    @DeleteMapping(path = "/status/completed")
    public void deleteCompletedTasks() {
        tasksServices.deleteCompletedTasks();
    }

    @Scheduled(cron = "0 * * * * *")
    @DeleteMapping(path = "/old")
    public void deleteOldTasks() {
        tasksServices.deleteOldTasks();
    }
}