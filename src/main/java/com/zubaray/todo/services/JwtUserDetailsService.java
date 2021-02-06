package com.zubaray.todo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zubaray.todo.models.TodoUser;
import com.zubaray.todo.repository.TodoUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private TodoUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username retrieve: {}", username);
        Optional<TodoUser> todoUser = userRepository.findByUsername(username).stream().findFirst();
        if (todoUser.isPresent()) {
            return new User(todoUser.get().getUsername(), todoUser.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
