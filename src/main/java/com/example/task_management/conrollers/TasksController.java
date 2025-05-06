package com.example.task_management.conrollers;

import com.example.task_management.model.Tasks;
import com.example.task_management.service.TasksService;
import com.example.task_management.utils.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {
    @Autowired
    private TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping(path = "")
    public List<Tasks> getAllTasks(){
        return tasksService.getAllTasks();
    }

    @GetMapping(path = "/{user_id}")
    public List<Tasks> getAllUserTasks(@PathVariable Long user_id){
        return tasksService.getAllUserTasks(user_id);
    }

    @PostMapping(path = "/create-task")
    public Tasks createTask(@RequestBody Tasks task){
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userPrinciple.getUser().getId();
        return tasksService.createTask(task , userId);
    }

    @PatchMapping(path = "/update-task/{task_id}")
    public Tasks updateTask(@RequestBody Tasks task , @PathVariable Long task_id){
        return tasksService.updateTask(task , task_id);
    }

    @DeleteMapping(path = "/delete-task/{task_id}")
    public Tasks deleteTask(@PathVariable Long task_id){
        return tasksService.deleteTask(task_id);
    }
}
