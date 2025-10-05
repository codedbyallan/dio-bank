package br.com.dio.auth;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private long nextId = 0L;

    public User save(String username, String password) {
        if (findByUsername(username) != null) return null;
        User u = new User(++nextId, username, password);
        users.add(u);
        return u;
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }
}