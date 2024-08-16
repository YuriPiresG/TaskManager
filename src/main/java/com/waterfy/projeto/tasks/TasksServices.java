package com.waterfy.projeto.tasks;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.exception.TaskNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TasksServices {

    private TasksRepository tasksRepository;

    public List<Task> getTasksWithParams(TaskStatus status) {
        if (status == null) {
            return tasksRepository.findAll();
        }
        return tasksRepository.findTaskByParameters(status);
    }

    public Task getTaskById(Long id) {
        return tasksRepository.findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public Task saveTask(Task task) {
        return tasksRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task foundTask = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));

        foundTask.setId(id);
        foundTask.setDescription(task.getDescription());
        foundTask.setDueDate(task.getDueDate());
        foundTask.setFinishedAt(task.getFinishedAt());
        foundTask.setStatus(task.getStatus());
        foundTask.setTitle(task.getTitle());

        return tasksRepository.save(foundTask);
    }

    public void deleteTask(Long id) {
        tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        tasksRepository.deleteById(id);
    }

    public void deleteAllTasks() {
        tasksRepository.deleteAll();
    }

    public void deleteCompletedTasks() {
        tasksRepository.deleteCompletedTasks();
    }
}
