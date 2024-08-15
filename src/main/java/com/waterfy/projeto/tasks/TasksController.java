package com.waterfy.projeto.tasks;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/tasks")
@RestController()
public class TasksController {
    private final TasksServices tasksServices;

    @GetMapping()
    public List<Task> getTasks() {
        return tasksServices.getTasks();
    }

    @GetMapping(path = "/{id}")
    public Task getTaskById(@PathVariable("id") Long id) {
        return tasksServices.getTaskById(id);
    }

    @GetMapping(path = "/uncompleted")
    public List<Task> getTaskById() {
        return tasksServices.getUncompletedTasks();
    }

    @PostMapping()
    public Task createTask(@Valid @RequestBody Task task) {
        return tasksServices.saveTask(task);
    }

    @PutMapping(path = "/{id}")
    public int putMethodName(@PathVariable Long id, @RequestBody Task task) {
        return tasksServices.updateTask(id, task);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasksServices.deleteTask(id);
    }

    @DeleteMapping()
    public void deleteAllTasks() {
        tasksServices.deleteAllTasks();
    }

    @DeleteMapping(path = "/completed")
    public void deleteCompletedTasks() {
        tasksServices.deleteCompletedTasks();
    }
}