package com.waterfy.projeto.tasks;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.exception.TaskNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TasksServices {

    private TasksRepository tasksRepository;

    public List<Task> getTasksWithParams(TaskStatus status, int page, int size) {
        if (status == null) {
            return tasksRepository.findAll(PageRequest.of(page, size)).toList();
        }
        Sort sort = Sort.by(Sort.Direction.ASC, "dueDate");
        Pageable pagedTasks = PageRequest.of(page, size, sort);
        return tasksRepository.findTaskByParameters(pagedTasks);
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

        foundTask = Task.builder()
                .id(id)
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .finishedAt(task.getFinishedAt()).status(task.getStatus()).build();

        return tasksRepository.save(foundTask);
    }

    public void deleteTask(Long id) {
        tasksRepository.findById(id)
                .orElseThrow(
                    () -> new TaskNotFoundException("Task with id " + id + " not found")
                    );
        tasksRepository.deleteById(id);
    }

    public void deleteAllTasks() {
        tasksRepository.deleteAll();
    }

    public void deleteCompletedTasks() {
        tasksRepository.deleteCompletedTasks();
    }

    public void deleteOldTasks() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        tasksRepository.deleteOldTasks(oneMonthAgo);
    }
}
