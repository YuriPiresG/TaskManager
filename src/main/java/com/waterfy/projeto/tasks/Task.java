package com.waterfy.projeto.tasks;

import static jakarta.persistence.GenerationType.SEQUENCE;

import java.time.LocalDate;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.enums.validation.ValueOfEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Table(name = "task")
@Entity(name = "task")
public class Task {

    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "task_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(55)")
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 55, message = "Title must be between 3 and 55 characters")
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "dueDate", nullable = false)
    @NotNull(message = "Due date cannot be empty")
    private LocalDate dueDate;

    @Column(name = "finishedAt", nullable = true)
    private LocalDate finishedAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @ValueOfEnum(enumClass = TaskStatus.class, message = "Status must be one of {enumClass}")
    private TaskStatus status;

}