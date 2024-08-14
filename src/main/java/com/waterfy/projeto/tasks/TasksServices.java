package com.waterfy.projeto.tasks;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TasksServices {
    private final TasksRepository tasksRepository;

    public List<Task> getTasks() {
        return tasksRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return tasksRepository.findById(id)
                .stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found!"));
    }

}
