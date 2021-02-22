package com.dwr.task.bootstrap;

import com.dwr.task.domain.Task;
import com.dwr.task.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final TaskRepository taskRepository;

    public Bootstrap(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {

        loadTasks();

    }

    private void loadTasks() {
        taskRepository.deleteAll().block();


        taskRepository.save(Task.builder()
                .description("apagar alarma").createdDate(System.currentTimeMillis()).isActive(true).build()).block();

        taskRepository.save(Task.builder()
                .description("tomar cafe").createdDate(System.currentTimeMillis()).isActive(true).build()).block();

        taskRepository.save(Task.builder()
                .description("leer correo").createdDate(System.currentTimeMillis()).isActive(true).build()).block();


        System.out.println("Tasks Loaded = " + taskRepository.count().block());
    }


}
