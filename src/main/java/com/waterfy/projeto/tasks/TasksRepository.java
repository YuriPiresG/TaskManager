package com.waterfy.projeto.tasks;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.waterfy.projeto.enums.TaskStatus;

import jakarta.transaction.Transactional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM task t WHERE t.status = ?1")
    List<Task> findTaskByParameters(TaskStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE task t SET t.title = ?1, t.description = ?2, t.dueDate = ?3, t.finishedAt = ?4, t.status = ?5 WHERE t.id = ?6")
    int updateTask(String title, String description, LocalDate dueDate, LocalDate finishedAt, TaskStatus status,
            Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM task t WHERE t.status = 'COMPLETED'")
    void deleteCompletedTasks();
}
