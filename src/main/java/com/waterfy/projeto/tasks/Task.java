package com.waterfy.projeto.tasks;

import static jakarta.persistence.GenerationType.SEQUENCE;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "dueDate", nullable = false)
    private LocalDate dueDate;

    @Column(name = "finishedAt", nullable = true)
    private LocalDate finishedAt;

    @Column(name = "status", nullable = false)
    private TaskStatus status;

}