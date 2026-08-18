package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.AppUser;
import br.com.meugestormedsistem.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AppUserService {
    private final AppUserRepository repository;

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
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

    public void delete(AppUser value) {
        repository.delete(value);
    }
}
