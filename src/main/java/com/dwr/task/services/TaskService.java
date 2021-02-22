package com.dwr.task.services;

import com.dwr.task.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Flux<Task> listAllTasks();

    Mono<Task> getTaskById(String id);

    Mono<Task> saveTask(Task task);

    Mono<Task> update(String id, Task task);

    Mono<Void> deleteTaskById(String id);
}
