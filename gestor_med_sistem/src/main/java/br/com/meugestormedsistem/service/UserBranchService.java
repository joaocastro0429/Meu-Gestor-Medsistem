package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.*;
import br.com.meugestormedsistem.repository.UserBranchRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserBranchService {
    private final UserBranchRepository repository;

    public UserBranchService(UserBranchRepository repository) {
        this.repository = repository;
    }

    public List<UserBranch> findAll() {
        return repository.findAll();
    }

    public Optional<UserBranch> findById(UserBranchId id) {
        return repository.findById(id);
    }

    public boolean existsById(UserBranchId id) {
        return repository.existsById(id);
    }

    public UserBranch save(UserBranch v) {
        return repository.save(v);
    }

    public void deleteById(UserBranchId id) {
        repository.deleteById(id);
    }
}
