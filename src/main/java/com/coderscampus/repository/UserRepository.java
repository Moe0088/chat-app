package com.coderscampus.repository;


import com.coderscampus.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private long idCounter = 1;


    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(idCounter++);
            users.add(user);
        }
        return user;

    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public User findById(long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    public boolean existsById (long id) {
        return findById(id) != null;
    }

    public Optional<User> findByName(String name) {
        return users.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
    }
}


