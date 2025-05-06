package com.example.task_management.conrollers;

import com.example.task_management.model.AppUser;
import com.example.task_management.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AppUserController {

    @Autowired
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> userLoginData) {
        String name = (String) userLoginData.get("name");
        String password = (String) userLoginData.get("password");
        return appUserService.verify(name , password);
    }

    @GetMapping(path = "/users")
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public AppUser getUserById(@PathVariable Long id) {
        return appUserService.getUserById(id);
    }

    @PostMapping(path = "/users/create")
    public AppUser createUser(@RequestBody AppUser user) {
        return appUserService.createUser(user);
    }

    @PutMapping(path = "/users/update/{id}")
    public AppUser updateUser(@RequestBody AppUser user, @PathVariable Long id) {
        return appUserService.updateUser(user , id);
    }

    @DeleteMapping(path = "/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        return appUserService.deleteUser(id);
    }
}
