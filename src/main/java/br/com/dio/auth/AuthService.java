package br.com.dio.auth;

public class AuthService {
    private final UserRepository repo;

    public AuthService(UserRepository repo) { this.repo = repo; }

    public User signUp(String username, String password) {
        if (repo.findByUsername(username) != null)
            throw new UserAlreadyExistsException("Username already taken: " + username);
        var created = repo.save(username, password);
        return created;
    }

    public User login(String username, String password) {
        var u = repo.findByUsername(username);
        if (u == null || !u.password().equals(password))
            throw new BadCredentialsException("Invalid username or password.");
        return u;
    }
}
