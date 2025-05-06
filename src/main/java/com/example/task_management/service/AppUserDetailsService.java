package com.example.task_management.service;

import com.example.task_management.model.AppUser;
import com.example.task_management.repository.UserRepository;
import com.example.task_management.utils.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(user);
        return new UserPrinciple(user);
    }

    public UserDetails loadUserByEmail(String email) throws Exception {
        AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserPrinciple(user);
    }

}
