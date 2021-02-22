package com.dwr.task.controllers;

import com.dwr.task.domain.Task;
import com.dwr.task.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
class TaskControllerTest {
    @Mock
    TaskService taskService;


    @InjectMocks
    TaskController taskController;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(taskController).build();
    }

    @Test
    void listAllTasks() {
        given(taskService.listAllTasks())
                .willReturn(Flux.just(Task.builder().description("Test1").build(),
                        Task.builder().description("Test2").build()));

        webTestClient.get().uri(TaskController.BASE_URL)
                .exchange()
                .expectBodyList(Task.class)
                .hasSize(2);

    }

    @Test
    void getById() {
        given(taskService.getTaskById(anyString()))
                .willReturn(Mono.just(Task.builder().description("Test").build()));

        webTestClient.get()
                .uri(TaskController.BASE_URL + "/1")
                .exchange()
                .expectBody(Task.class);
    }

    @Test
    void createCategoryTest() {
        Mono<Task> categoryToSave = Mono.just(Task.builder().description("test").build());
        given(taskService.saveTask(any(Task.class)))
                .willReturn(Mono.just(Task.builder().description("123").build()));

        webTestClient.post()
                .uri(TaskController.BASE_URL)
                .body(categoryToSave, Task.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateCategoryTest() {
        given(taskService.update(anyString(), any(Task.class)))
                .willReturn(Mono.just(Task.builder().description("aaa").build()));
        //we want to update this obj
        Mono<Task> categoryToUpdate = Mono.just(Task.builder().description("test").build());

        webTestClient.put()
                .uri(TaskController.BASE_URL + "/1")
                .body(categoryToUpdate, Task.class)
                .exchange()
                .expectStatus()
                .isOk();

    }


    @Test
    void deleteCategoryTest() {
        webTestClient.delete()
                .uri(TaskController.BASE_URL + "/id")
                .exchange()
                .expectStatus()
                .isOk();
    }
}