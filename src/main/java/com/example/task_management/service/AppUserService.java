package com.example.task_management.service;

import com.example.task_management.model.AppUser;
import com.example.task_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    public AppUserService(BCryptPasswordEncoder bCryptPasswordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder1, UserRepository userRepository) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    public List<AppUser> getAllUsers(){
        return userRepository.findAll();
    }

    public AppUser getUserById(Long id){
        return userRepository.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public AppUser createUser(AppUser appUser){
        Optional<AppUser> user = userRepository.findByEmail(appUser.getEmail());
        if(user.isPresent()) {
            throw new IllegalStateException("User Already Present with this email");
        }
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    public AppUser updateUser(AppUser appUser , Long id){
        Optional<AppUser> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new IllegalStateException("User not found");
        }
        user.get().setName(appUser.getName());
        user.get().setEmail(appUser.getEmail());
        user.get().setPassword(appUser.getPassword());
        return userRepository.save(user.get());
    }

    public String deleteUser(Long id){
        Optional<AppUser> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new IllegalStateException("User not found");
        }
        userRepository.deleteById(id);
        return "User Deleted Successfully";
    }

    public String verify(String name, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
        );
        if(authentication.isAuthenticated())
            return (String) jwtService.generateJWTToken(name);
        return "Fail";
    }
}
