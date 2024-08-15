package com.waterfy.projeto.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterfy.projeto.exception.TaskNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TasksServices {
    @Autowired
    private final TasksRepository tasksRepository;

    public List<Task> getTasks() {
        return tasksRepository.findAll();
    }
    public List<Task> getUncompletedTasks() {
        return tasksRepository.findUncompletedTasks();
    }

    public Task getTaskById(Long id) {
        return tasksRepository.findById(id)
                .stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public Task saveTask(Task task) {
        return tasksRepository.save(task);
    }

    public int updateTask(Long id, Task task) {
        tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        return tasksRepository.updateTask(
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getFinishedAt(),
                task.getStatus(),
                id);
    }
}
