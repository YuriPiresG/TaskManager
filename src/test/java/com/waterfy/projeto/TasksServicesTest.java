package com.waterfy.projeto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.exception.TaskNotFoundException;
import com.waterfy.projeto.tasks.Task;
import com.waterfy.projeto.tasks.TasksRepository;
import com.waterfy.projeto.tasks.TasksServices;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TasksServicesTest {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TasksServices tasksServices;

    @BeforeEach
    public void setUp() {
        tasksRepository.deleteAll();
    }

    @Test
    public void testGetTasksWithParams_PaginationAndSorting() {
        Task task1 = Task.builder()
                .title("title")
                .dueDate(LocalDate.now().plusDays(1))
                .status(TaskStatus.COMPLETED)
                .build();

        Task task2 = Task.builder()
                .title("title")
                .dueDate(LocalDate.now().plusDays(2))
                .status(TaskStatus.COMPLETED)
                .build();

        tasksRepository.save(task1);
        tasksRepository.save(task2);

        List<Task> result = tasksServices.getTasksWithParams(null, 0, 1);

        assertEquals(1, result.size());
        assertEquals(task1.getDueDate(), result.get(0).getDueDate());
    }

    @Test
    public void testGetTaskById_ExistingId() {
        Task task = Task.builder()
                .title("title")
                .dueDate(LocalDate.now().plusDays(1))
                .finishedAt(LocalDate.now())
                .status(TaskStatus.COMPLETED)
                .build();
    
        Task savedTask = tasksRepository.save(task);
    
        Task result = tasksServices.getTaskById(savedTask.getId());
    
        assertEquals(savedTask.getId(), result.getId());
    }

    @Test
    public void testGetTaskById_NonExistingId() {
        assertThrows(TaskNotFoundException.class, () -> tasksServices.getTaskById(1L));
    }

    @Test
    public void testSaveTask() {
        Task task = Task.builder()
                .title("title")
                .dueDate(LocalDate.now().plusDays(1))
                .status(TaskStatus.COMPLETED)
                .build();
        Task result = tasksServices.saveTask(task);

        assertNotNull(result.getId());
    }

    @Test
    public void testUpdateTask_ExistingId() {
        Task task = Task.builder()
                .title("title")
                .description("Initial Description")
                .dueDate(LocalDate.now().plusDays(1))
                .status(TaskStatus.COMPLETED)
                .build();

        tasksServices.saveTask(task);

        Task updatedTask = Task.builder()
                .title("titleUpdated")
                .description("Updated Description")
                .dueDate(LocalDate.now().plusDays(2))
                .status(TaskStatus.PENDING)
                .build();
        Task result = tasksServices.updateTask(task.getId(), updatedTask);

        assertEquals("Updated Description", result.getDescription());
        assertEquals("titleUpdated", result.getTitle());
        assertEquals(LocalDate.now().plusDays(2), result.getDueDate());
        assertEquals(TaskStatus.PENDING, result.getStatus());
    }

    @Test
    public void testUpdateTask_NonExistingId() {
        Task task = Task.builder().id(2L).build();
        assertThrows(TaskNotFoundException.class, () -> tasksServices.updateTask(1L, task));
    }

    @Test
    public void testDeleteTask_ExistingId() {
        Task task = Task.builder()
                .title("title")
                .dueDate(LocalDate.now().plusDays(1))
                .status(TaskStatus.COMPLETED)
                .build();

        tasksRepository.save(task);

        tasksServices.deleteTask(task.getId());

        assertFalse(tasksRepository.findById(1L).isPresent());
    }

    @Test
    public void testDeleteTask_NonExistingId() {
        assertThrows(TaskNotFoundException.class, () -> tasksServices.deleteTask(1L));
    }

    @Test
    public void testDeleteAllTasks() {
        Task task1 = Task.builder()
                .title("title")
                .dueDate(LocalDate.now().plusDays(1))
                .status(TaskStatus.COMPLETED)
                .build();

        Task task2 = Task.builder()
                .title("title")
                .dueDate(LocalDate.now().plusDays(1))
                .status(TaskStatus.COMPLETED)
                .build();

        tasksRepository.save(task1);
        tasksRepository.save(task2);

        tasksServices.deleteAllTasks();

        assertEquals(0, tasksRepository.findAll().size());
    }

    @Test
    public void testDeleteCompletedTasks() {
        Task task1 = Task.builder()
                .title("Title")
                .dueDate(LocalDate.now())
                .status(TaskStatus.COMPLETED)
                .build();

        Task task2 = Task.builder()
                .title("Title")
                .dueDate(LocalDate.now())
                .status(TaskStatus.PENDING)
                .build();

        tasksRepository.save(task1);
        tasksRepository.save(task2);

        tasksServices.deleteCompletedTasks();

        assertEquals(1, tasksRepository.findAll().size());
        assertEquals(TaskStatus.PENDING, tasksRepository.findAll().get(0).getStatus());
    }

    // @Test
    // public void testDeleteOldTasks() {
    //     Task task1 = Task.builder().title("Title")
    //             .dueDate(LocalDate.now().minusMonths(2))
    //             .status(TaskStatus.COMPLETED)
    //             .build();

    //     Task task2 = Task.builder().title("Title")
    //             .dueDate(LocalDate.now().minusDays(10))
    //             .status(TaskStatus.PENDING)
    //             .build();

    //     tasksRepository.save(task1);
    //     tasksRepository.save(task2);

    //     tasksServices.deleteOldTasks();

    //     assertEquals(1, tasksRepository.findAll().size());
    //     assertEquals(task2.getDueDate(), tasksRepository.findAll().get(0).getDueDate());
    // }
}