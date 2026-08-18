package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.repository.UserPermissionRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserPermissionService {
    private final UserPermissionRepository repository;

    public UserPermissionService(UserPermissionRepository repository) {
        this.repository = repository;
    }

    public List<UserPermission> findAll() {
        return repository.findAll();
    }

    public Optional<UserPermission> findById(UserPermissionId id) {
        return repository.findById(id);
    }

    public boolean existsById(UserPermissionId id) {
        return repository.existsById(id);
    }

    public UserPermission save(UserPermission v) {
        return repository.save(v);
    }

    public void deleteById(UserPermissionId id) {
        repository.deleteById(id);
    }
}
