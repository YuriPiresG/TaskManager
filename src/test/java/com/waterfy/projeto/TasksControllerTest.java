package com.waterfy.projeto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.waterfy.projeto.enums.TaskStatus;
import com.waterfy.projeto.exception.TaskNotFoundException;
import com.waterfy.projeto.tasks.Task;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TasksControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private com.waterfy.projeto.tasks.TasksServices tasksServices;

        @Test
        public void testGetTasks() throws Exception {
                Task task = Task.builder().title("Title").description("Description")
                                .dueDate(LocalDate.parse("2023-01-01")).finishedAt(LocalDate.parse("2023-01-01"))
                                .status(TaskStatus.COMPLETED).build();
                tasksServices.saveTask(task);
        }

        @Test
        public void testGetTaskById() throws Exception {
                Task task = Task.builder().title("Title").description("Description")
                                .dueDate(LocalDate.parse("2023-01-01")).finishedAt(LocalDate.parse("2023-01-01"))
                                .status(TaskStatus.COMPLETED).build();
                task = tasksServices.saveTask(task);

                mockMvc.perform(get("/tasks/" + task.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Title"));
        }

        @Test
        public void testCreateTask() throws Exception {
                String taskJson = "{\"title\":\"Title\",\"description\":\"Description\",\"dueDate\":\"2023-01-01\",\"finishedAt\":\"2023-01-01\",\"status\":\"COMPLETED\"}";

                mockMvc.perform(post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(taskJson))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.title").value("Title"));
        }

        @Test
        public void testPutUpdateTask() throws Exception {
                Task task = Task.builder().title("Title").description("Description")
                                .dueDate(LocalDate.parse("2023-01-01")).finishedAt(LocalDate.parse("2023-01-01"))
                                .status(TaskStatus.COMPLETED).build();
                task = tasksServices.saveTask(task);

                String updatedTaskJson = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"dueDate\":\"2023-01-01\",\"finishedAt\":\"2023-01-01\",\"status\":\"COMPLETED\"}";

                mockMvc.perform(put("/tasks/" + task.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedTaskJson))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Updated Title"));
        }

        @Test
        public void testDeleteTask() throws Exception {
                Task task = Task.builder()
                                .title("Title")
                                .description("Description")
                                .dueDate(LocalDate.parse("2023-01-01"))
                                .finishedAt(LocalDate.parse("2023-01-01"))
                                .status(TaskStatus.COMPLETED)
                                .build();

                task = tasksServices.saveTask(task);

                mockMvc.perform(delete("/tasks/" + task.getId()))
                                .andExpect(status().isOk());

                assertThrows(TaskNotFoundException.class, () -> tasksServices.getTaskById(1L));
        }

        @Test
        public void testDeleteAllTasks() throws Exception {
                Task task1 = Task.builder().title("Title1").description("Description1")
                                .dueDate(LocalDate.parse("2023-01-01")).finishedAt(LocalDate.parse("2023-01-01"))
                                .status(TaskStatus.COMPLETED).build();
                Task task2 = Task.builder().title("Title2").description("Description2")
                                .dueDate(LocalDate.parse("2023-01-01")).finishedAt(LocalDate.parse("2023-01-01"))
                                .status(TaskStatus.COMPLETED).build();
                tasksServices.saveTask(task1);
                tasksServices.saveTask(task2);

                mockMvc.perform(delete("/tasks"))
                                .andExpect(status().isOk());

                // Provide page and size parameters
                assertThat(tasksServices.getTasksWithParams(null, 0, 10)).isEmpty();
        }

        @Test
        public void testDeleteCompletedTasks() throws Exception {
            Task task1 = Task.builder()
                            .title("Title1")
                            .description("Description1")
                            .dueDate(LocalDate.parse("2023-01-01"))
                            .finishedAt(LocalDate.parse("2023-01-01"))
                            .status(TaskStatus.COMPLETED).build();
            Task task2 = Task.builder()
                            .title("Title2").description("Description2")
                            .dueDate(LocalDate.parse("2023-01-01"))
                            .finishedAt(LocalDate.parse("2023-01-01"))
                            .status(TaskStatus.PENDING).build();
            tasksServices.saveTask(task1);
            tasksServices.saveTask(task2);
        
            mockMvc.perform(delete("/tasks/status/completed"))
                            .andExpect(status().isOk());
        
            assertThat(mockMvc.perform(get("/tasks/status/completed")).andReturn().getResponse().getContentAsString()).isEqualTo("");
            assertThat(tasksServices.getTasksWithParams(null, 0, 10)).isNotEmpty();
        }
}