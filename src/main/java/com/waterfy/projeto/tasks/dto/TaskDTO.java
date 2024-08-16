package com.waterfy.projeto.tasks.dto;

import java.time.LocalDate;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.enums.validation.ValueOfEnum;
import com.waterfy.projeto.tasks.Task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 55, message = "Title must be between 1 and 55 characters")
    private String title;

    private String description;

    @NotNull(message = "Due date cannot be empty")
    private LocalDate dueDate;

    private LocalDate finishedAt;

    @NotNull(message = "Status cannot be empty")
    @ValueOfEnum(enumClass = TaskStatus.class, message = "Status must be one of {enumClass}")
    private TaskStatus status;

    public static TaskDTO fromTask(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setFinishedAt(task.getFinishedAt());
        taskDTO.setStatus(task.getStatus());
        return taskDTO;
    }
}