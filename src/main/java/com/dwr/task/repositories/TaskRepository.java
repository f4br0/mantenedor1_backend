package com.dwr.task.repositories;

import com.dwr.task.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {}
