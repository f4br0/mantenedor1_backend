package com.dwr.task.controllers;

import com.dwr.task.domain.Task;
import com.dwr.task.services.TaskService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(TaskController.BASE_URL)
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TaskController {
    public static final String BASE_URL = "/api/v1/tasks";

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Flux<Task> listAllTasks() {
        return taskService.listAllTasks();
    }

    @GetMapping("/{id}")
    public Mono<Task> getById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @Schema(implementation = Task.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Task> create(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.update(id, task);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable String id) {
        return taskService.deleteTaskById(id);
    }

}
