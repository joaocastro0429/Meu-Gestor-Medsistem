package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.AppUser;
import br.com.meugestormedsistem.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class AppUserService {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUser> findAll() {
        return repository.findAll();
    }

    public Optional<AppUser> findById(UUID id) {
        return repository.findById(id);
    }

    public AppUser save(AppUser value) {
        return repository.save(value);
    }

    public AppUser criar(AppUser user, String password) {
        if (repository.existsByEmailValueIgnoreCase(user.getEmail().getValue())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        user.setPasswordHash(passwordEncoder.encode(password));
        return repository.save(user);
    }

    public Optional<AppUser> findByEmail(String email) {
        return repository.findByEmailValueIgnoreCase(email);
    }

    public void delete(AppUser value) {
        repository.delete(value);
    }
}
