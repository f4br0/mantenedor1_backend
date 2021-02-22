package com.dwr.task.services;

import com.dwr.task.config.TaskValidator;
import com.dwr.task.domain.Task;
import com.dwr.task.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Flux<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Mono<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    @Override
    public Mono<Task> saveTask(Task task) {
        Errors errors = validate(task);

        if (errors == null || errors.getAllErrors().isEmpty()) {
            task.setCreatedDate(System.currentTimeMillis());
            task.setIsActive(true);
            return taskRepository.save(task);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }

    }

    private Errors validate(Task task) {
        Validator validator = new TaskValidator();
        Errors errors = new BeanPropertyBindingResult(
                task,
                Task.class.getName());
        validator.validate(task, errors);
        return errors;
    }

    @Override
    public Mono<Task> update(String id, Task task) {
        Errors errors = validate(task);
        if (errors == null || errors.getAllErrors().isEmpty()) {
            return taskRepository.findById(id)
                    .flatMap(foundTask -> {
                        task.setId(foundTask.getId());
                        return taskRepository.save(task);
                    })
                    .switchIfEmpty(Mono.error(new Exception("Not Found")));

        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }


    }


    @Override
    public Mono<Void> deleteTaskById(String id) {
        return taskRepository.deleteById(id);
    }
}
