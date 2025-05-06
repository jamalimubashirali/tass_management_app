package com.example.task_management.service;

import com.example.task_management.model.AppUser;
import com.example.task_management.model.Tasks;
import com.example.task_management.repository.TasksRepository;
import com.example.task_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {

    @Autowired
    private final TasksRepository tasksRepository;

    @Autowired
    private final UserRepository userRepository;

    public TasksService(TasksRepository tasksRepository, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }

    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    public List<Tasks> getAllUserTasks(Long userId) {
        if(userId == null){
            throw new IllegalStateException("User Id is null");
        }

        List<Tasks> tasks = tasksRepository.findByUserId(userId);

        if(tasks.isEmpty()){
            throw new IllegalStateException("No Tasks Found for this User Id : " + userId);
        }

        return tasks;
    }

    public Tasks createTask(Tasks task , Long user_id) {
        AppUser user = userRepository.findById(user_id).orElseThrow(
                () -> new IllegalStateException("User Not Found")
        );

        task.setUser(user);
        return tasksRepository.save(task);
    }

    public Tasks updateTask(Tasks task, Long taskId) {
        Tasks existingTask = tasksRepository.findById(taskId).orElseThrow(
                () -> new IllegalStateException("Task Not Found")
        );

        existingTask.setTaskName(task.getTaskName());
        existingTask.setTaskDescription(task.getTaskDescription());
        existingTask.setTaskStatus(task.getTaskStatus());

        return tasksRepository.save(existingTask);
    }

    public Tasks deleteTask(Long taskId) {
        Tasks existingTask = tasksRepository.findById(taskId).orElseThrow(
                () -> new IllegalStateException("Task Not Found")
        );

        tasksRepository.deleteById(taskId);

        return existingTask;
    }
}
