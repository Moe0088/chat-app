package com.coderscampus.service;

import com.coderscampus.domain.User;
import com.coderscampus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Marks this as a Spring-managed “service” bean, so it can be @Autowired elsewhere
@Service
public class UserService {

    // Injects your in-memory UserRepository so this service can delegate persistence
    @Autowired
    private UserRepository userRepo;

    /**
     * Finds or creates a User by name.
     * @param name the raw name input from the client
     * @return an existing User with that name, or a newly created one
     * @throws IllegalArgumentException if name is null or empty
     */
    public User createUser(String name) {
        // 1) Validate: no nulls or blank strings allowed
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        // 2) Trim off extra whitespace
        String trimmed = name.trim();

        // 3) Check repository for an already-saved User with this exact name
        Optional<User> existing = userRepo.findByName(trimmed);
        if (existing.isPresent()) {
            // 3a) If we found one, return it (prevents duplicates)
            return existing.get();
        }

        // 4) Otherwise, build a brand-new User domain object
        User user = new User();
        user.setName(trimmed);

        // 5) Save it (repository will assign a new ID) and return it
        return userRepo.saveUser(user);
    }

    /**
     * Looks up a User by their ID.
     * @param id the numeric user ID
     * @return the User if found, or null if not
     */
    public User findUserById(Long id) {
        return userRepo.findById(id);
    }

    /**
     * Returns every User we have in memory.
     * Useful for listing or debugging.
     */
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Exposes the repository’s findByName for external lookup.
     * @param name exact name to search
     * @return optional User
     */
    public Optional<User> findByName(String name) {
        return userRepo.findByName(name);
    }
}
