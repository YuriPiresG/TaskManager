package com.waterfy.projeto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.tasks.Task;
import com.waterfy.projeto.tasks.TasksController;
import com.waterfy.projeto.tasks.TasksServices;

public class TasksControllerTest {

        private MockMvc mockMvc;

        @Mock
        private TasksServices tasksServices;

        @InjectMocks
        private TasksController tasksController;

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(tasksController).build();
        }

        @Test
        public void testGetTasks() throws Exception {
                Task task = new Task();
                task.setId(1L);
                List<Task> tasks = Arrays.asList(task);
                when(tasksServices.getTasksWithParams(null)).thenReturn(tasks);

                mockMvc.perform(get("/tasks"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1));
        }

        @Test
        public void testGetTaskById() throws Exception {
                Task task = new Task();
                task.setId(1L);
                when(tasksServices.getTaskById(1L)).thenReturn(task);

                mockMvc.perform(get("/tasks/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1));

                verify(tasksServices, times(1)).getTaskById(1L);
        }

        @Test
        public void testCreateTask() throws Exception {
                Task task = new Task();
                task.setId(1L);
                task.setTitle("Title");
                task.setDescription("Description");
                task.setDueDate(LocalDate.parse("2023-01-01"));
                task.setFinishedAt(LocalDate.parse("2023-01-01"));
                task.setStatus(TaskStatus.COMPLETED);

                when(tasksServices.saveTask(any(Task.class))).thenReturn(task);

                mockMvc.perform(post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"title\":\"Title\",\"description\":\"Description\",\"dueDate\":\"2023-01-01\",\"finishedAt\":\"2023-01-01\",\"status\":\"COMPLETED\"}"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1));

                verify(tasksServices, times(1)).saveTask(any(Task.class));
        }

        @Test
        public void testPutMethodName() throws Exception {
                when(tasksServices.updateTask(eq(1L), any(Task.class))).thenReturn(1);

                mockMvc.perform(put("/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"startDate\":\"2023-01-01\",\"endDate\":\"2023-01-01\",\"status\":\"COMPLETED\"}"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("1"));

                verify(tasksServices, times(1)).updateTask(eq(1L), any(Task.class));
        }

        @Test
        public void testDeleteTask() throws Exception {
                doNothing().when(tasksServices).deleteTask(1L);

                mockMvc.perform(delete("/tasks/1"))
                                .andExpect(status().isOk());

                verify(tasksServices, times(1)).deleteTask(1L);
        }

        @Test
        public void testDeleteAllTasks() throws Exception {
                doNothing().when(tasksServices).deleteAllTasks();

                mockMvc.perform(delete("/tasks"))
                                .andExpect(status().isOk());

                verify(tasksServices, times(1)).deleteAllTasks();
        }

        @Test
        public void testDeleteCompletedTasks() throws Exception {
                doNothing().when(tasksServices).deleteCompletedTasks();

                mockMvc.perform(delete("/tasks/completed"))
                                .andExpect(status().isOk());

                verify(tasksServices, times(1)).deleteCompletedTasks();
        }
}