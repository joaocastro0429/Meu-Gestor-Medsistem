package br.com.meugestormedsistem.service;

import br.com.meugestormedsistem.domain.Branch;
import br.com.meugestormedsistem.repository.BranchRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BranchService {
    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    public List<Branch> findAll() {
        return repository.findAll();
    }

    public Optional<Branch> findById(UUID id) {
        return repository.findById(id);
    }

    public Branch save(Branch value) {
        return repository.save(value);
    }

    public void delete(Branch value) {
        repository.delete(value);
    }
}
