package com.coderscampus.service;

import com.coderscampus.domain.User;
import com.coderscampus.repository.ChannelRepository;
import com.coderscampus.repository.MessageRepository;
import com.coderscampus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;


    public User createUser(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        String trimmed = name.trim();
        Optional<User> existing = userRepo.findByName(trimmed);
        if(existing.isPresent()) {
            return existing.get();
        }
        User user = new User();
        user.setName(trimmed);
        return userRepo.save(user);
    }

    public User findUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }
    public Optional<User> findByName(String name) {
        return userRepo.findByName(name);
    }
}
