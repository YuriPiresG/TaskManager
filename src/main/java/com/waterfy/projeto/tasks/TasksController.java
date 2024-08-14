package com.waterfy.projeto.tasks;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TasksController {
    @GetMapping("/tasks")
    public String getTasks() {
        return "List of tasks";
    }
}