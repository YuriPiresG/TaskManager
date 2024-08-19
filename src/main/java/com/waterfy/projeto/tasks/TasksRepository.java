package com.waterfy.projeto.tasks;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM task t WHERE t.status = ?1")
    List<Task> findTaskByParameters(Pageable page);

    @Transactional
    @Modifying
    @Query("DELETE FROM task t WHERE t.status = 'COMPLETED'")
    void deleteCompletedTasks();

    @Modifying
    @Transactional
    @Query("DELETE FROM task t WHERE t.finishedAt < :oneMonthAgo")
    void deleteOldTasks(@Param("oneMonthAgo") LocalDate oneMonthAgo);
}