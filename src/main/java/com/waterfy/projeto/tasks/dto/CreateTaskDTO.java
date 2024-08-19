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
public class CreateTaskDTO {

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 55, message = "Title must be between 3 and 55 characters")
    private String title;

    private String description;

    @NotNull(message = "Due date cannot be empty")
    private LocalDate dueDate;

    private LocalDate finishedAt;

    @ValueOfEnum(enumClass = TaskStatus.class, message = "Status must be one of {enumClass}")
    private TaskStatus status;

    public Task toTask() {
        Task task = Task.builder().title(title).description(description).dueDate(dueDate).finishedAt(finishedAt).status(status).build();
        return task;
    }
}