package com.waterfy.projeto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.exception.TaskNotFoundException;
import com.waterfy.projeto.tasks.Task;
import com.waterfy.projeto.tasks.TasksRepository;
import com.waterfy.projeto.tasks.TasksServices;

public class TasksServicesTest {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksServices tasksServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTasksWithParams_NullStatus() {
        List<Task> tasks = Arrays.asList(new Task(),
                new Task());
        when(tasksRepository.findAll()).thenReturn(tasks);

        List<Task> result = tasksServices.getTasksWithParams(null);

        assertEquals(2, result.size());
        verify(tasksRepository, times(1)).findAll();
    }

    @Test
    public void testGetTasksWithParams_NonNullStatus() {
        TaskStatus status = TaskStatus.COMPLETED;
        List<Task> tasks = Arrays.asList(new Task(),
                new Task());
        when(tasksRepository.findTaskByParameters(status)).thenReturn(tasks);

        List<Task> result = tasksServices.getTasksWithParams(status);

        assertEquals(2, result.size());
        verify(tasksRepository, times(1)).findTaskByParameters(status);
    }

    @Test
    public void testGetTaskById_ExistingId() {
        Task task = new Task();
        task.setId(1L);
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = tasksServices.getTaskById(1L);

        assertEquals(1L, result.getId());
        verify(tasksRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTaskById_NonExistingId() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> tasksServices.getTaskById(1L));
        verify(tasksRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveTask() {
        Task task = new Task();
        when(tasksRepository.save(task)).thenReturn(task);

        Task result = tasksServices.saveTask(task);

        assertEquals(task, result);
        verify(tasksRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTask_ExistingId() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Original Title");
        task.setDescription("Original Description");
        task.setDueDate(LocalDate.now());
        task.setFinishedAt(LocalDate.now());
        task.setStatus(TaskStatus.PENDING);

        tasksRepository.save(task);

        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));
        when(tasksRepository.updateTask(
                "Updated Title",
                "Updated Description",
                LocalDate.now(),
                LocalDate.now(),
                TaskStatus.COMPLETED,
                1L)).thenReturn(1);

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setDueDate(LocalDate.now());
        updatedTask.setFinishedAt(LocalDate.now());
        updatedTask.setStatus(TaskStatus.COMPLETED);

        int result = tasksServices.updateTask(1L, updatedTask);

        assertEquals(1, result);
    }

    @Test
    public void testUpdateTask_NonExistingId() {
        Task task = new Task();
        when(tasksRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> tasksServices.updateTask(1L, task));
        verify(tasksRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteTask_ExistingId() {
        Task task = new Task();
        task.setId(1L);
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));

        tasksServices.deleteTask(1L);

        verify(tasksRepository, times(1)).findById(1L);
        verify(tasksRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTask_NonExistingId() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> tasksServices.deleteTask(1L));
        verify(tasksRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteAllTasks() {
        tasksServices.deleteAllTasks();

        verify(tasksRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteCompletedTasks() {
        tasksServices.deleteCompletedTasks();

        verify(tasksRepository, times(1)).deleteCompletedTasks();
    }
}